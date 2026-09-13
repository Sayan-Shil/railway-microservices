package com.rare.railwaycoreservice.service.impl;

import com.rare.payload.request.train.TrainRequest;
import com.rare.payload.response.ApiResponse;
import com.rare.payload.response.train.TrainResponse;
import com.rare.railwaycoreservice.entity.Coach;
import com.rare.railwaycoreservice.entity.Railway;
import com.rare.railwaycoreservice.entity.Train;
import com.rare.railwaycoreservice.mapper.TrainMapper;
import com.rare.railwaycoreservice.repository.CoachRepository;
import com.rare.railwaycoreservice.repository.RailwayRepository;
import com.rare.railwaycoreservice.repository.TrainRepository;
import com.rare.railwaycoreservice.service.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final RailwayRepository railwayRepository;
    private final TrainRepository trainRepository;
    private final CoachRepository coachRepository;


    @Override
    public TrainResponse createTrain(TrainRequest request, Long ownerId) {

        Railway railway = railwayRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Railway not found with owner Id"));

        Train train = TrainMapper.toEntity(request);
        train.setRailway(railway);

        if (request.getCurrentRailwayId() != null) {
            railwayRepository.findById(request.getCurrentRailwayId())
                    .orElseThrow(() -> new IllegalArgumentException("Current railway not found"));
        }

        return TrainMapper.toResponse(trainRepository.save(train));
    }

    @Override
    public List<TrainResponse> createBulkTrains(
            List<Map<Long, TrainRequest>> requests) {

        List<Train> trains = new ArrayList<>();

        for (Map<Long, TrainRequest> requestMap : requests) {

            for (Map.Entry<Long, TrainRequest> entry : requestMap.entrySet()) {

                Long ownerId = entry.getKey();
                TrainRequest request = entry.getValue();

                Railway railway = railwayRepository.findById(ownerId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Railway not found: " + ownerId
                                ));

                Train train = TrainMapper.toEntity(request);
                train.setRailway(railway);

                trains.add(train);
            }
        }

        return trainRepository.saveAll(trains)
                .stream()
                .map(TrainMapper::toResponse)
                .toList();
    }

    @Override
    public TrainResponse getTrainById(Long id)
    {

        Train train = trainRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Train not found"));

        return TrainMapper.toResponse(train);
    }

    @Override
    public Page<TrainResponse> getAllTrainsByOwnerId(
            Long ownerId,
            Pageable pageable) {

        return trainRepository.findByRailwayId(ownerId, pageable)
                .map(TrainMapper::toResponse);
    }

    @Override
    public TrainResponse updateTrain(
            TrainRequest request,
            Long id) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Train not found"));

        train.setCode(request.getCode());
        train.setName(request.getName());
        train.setModel(request.getModel());
        train.setManufacturer(request.getManufacturer());
        train.setYearOfManufacturer(request.getYearOfManufacturer());
        train.setRegistrationDate(request.getRegistrationDate());
        train.setNextMaintenanceDate(request.getNextMaintenanceDate());
        train.setTrainType(request.getTrainType());
        train.setOperatingSpeedKmh(request.getOperatingSpeedKmh());
        train.setRouteDistanceKm(request.getRouteDistanceKm());
        train.setCurrentRailwayId(request.getCurrentRailwayId());

        return TrainMapper.toResponse(trainRepository.save(train));
    }

    @Override
    public void deleteTrain(Long id, Long ownerId) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Train not found"));

        if (train.getRailway() == null
                || !train.getRailway().getId().equals(ownerId)) {
            throw new IllegalArgumentException(
                    "Train does not belong to this railway"
            );
        }

        trainRepository.delete(train);
    }

    @Override
    public void deleteAllTrain() {
        trainRepository.deleteAll();
    }

    @Override
    public ApiResponse<TrainResponse> addCoach(Long id, Long coachId) {
        Train train = trainRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Train with this id doesn't exists")
        );
        Coach coach = coachRepository.findById(coachId).orElseThrow(
                ()-> new IllegalArgumentException("Train with this id doesn't exists")
        );

        train.addCoach(coach);
        return ApiResponse.<TrainResponse>builder()
                .success(true)
                .message("Coach with id " +coach.getId()+ " has successfully be added to the Train " +train.getName()+ " with Id " +train.getId())
                .data(TrainMapper.toResponse(train))
                .build();
    }

    @Override
    public ApiResponse<TrainResponse> removeCoach(Long id, Long coachId) {

        Train train = trainRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Train with this id doesn't exists")
        );
        Coach coach = coachRepository.findById(coachId).orElseThrow(
                ()-> new IllegalArgumentException("Train with this id doesn't exists")
        );

        train.removeCoach(coach);
        return ApiResponse.<TrainResponse>builder()
                .success(true)
                .message("Coach with id " +coach.getId() +" has successfully be added to the Train " +train.getName()+ " with Id " +train.getId())
                .data(TrainMapper.toResponse(train))
                .build();
    }

}
