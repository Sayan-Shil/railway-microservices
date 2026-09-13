package com.rare.railwaycoreservice.service.impl;

import com.rare.payload.request.train.CoachRequest;
import com.rare.payload.response.train.CoachResponse;
import com.rare.railwaycoreservice.entity.Coach;
import com.rare.railwaycoreservice.mapper.CoachMapper;
import com.rare.railwaycoreservice.repository.CoachRepository;
import com.rare.railwaycoreservice.repository.TrainRepository;
import com.rare.railwaycoreservice.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final TrainRepository trainRepository;

    @Override
    public CoachResponse createCoach(CoachRequest coachRequest) {

        if (coachRepository.existsByCoachCode(coachRequest.getCoachCode())) {
            throw new IllegalArgumentException("Coach code already exists");
        }

        Coach coach = new Coach();

        coach.setCoachCode(coachRequest.getCoachCode());
        coach.setType(coachRequest.getType());
        coach.setCapacity(coachRequest.getCapacity());

        if (coachRequest.getTrainId() != null) {
            coach.setTrain(
                    trainRepository.findById(coachRequest.getTrainId())
                            .orElseThrow(() ->
                                    new IllegalArgumentException("Train not found"))
            );
        }
        return CoachMapper.toResponse(coachRepository.save(coach));
    }

    @Override
    public List<CoachResponse> createCoaches(List<CoachRequest> coachRequests) {

        List<Coach> coaches = coachRequests.stream()
                .map(request -> {

                    if (coachRepository.existsByCoachCode(request.getCoachCode())) {
                        throw new IllegalArgumentException(
                                "Coach code already exists: " + request.getCoachCode()
                        );
                    }

                    Coach coach = new Coach();
                    coach.setCoachCode(request.getCoachCode());
                    coach.setType(request.getType());
                    coach.setCapacity(request.getCapacity());

                    if (request.getTrainId() != null) {
                        coach.setTrain(
                                trainRepository.findById(request.getTrainId())
                                        .orElseThrow(() ->
                                                new IllegalArgumentException(
                                                        "Train not found: "
                                                                + request.getTrainId()
                                                ))
                        );
                    }

                    return coach;
                })
                .toList();

        return coachRepository.saveAll(coaches)
                .stream()
                .map(CoachMapper::toResponse)
                .toList();
    }

    @Override
    public CoachResponse getCoachById(Long id) {

        Coach coach = coachRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Coach not found"));

        return CoachMapper.toResponse(coach);
    }

    @Override
    public List<CoachResponse> getAllCoaches() {
        return coachRepository.findAll()
                .stream()
                .map(CoachMapper::toResponse)
                .toList();
    }

    @Override
    public CoachResponse updateCoach(
            Long id,
            CoachRequest coachRequest) {

        Coach coach = coachRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Coach not found"));

        coach.setCoachCode(coachRequest.getCoachCode());
        coach.setType(coachRequest.getType());
        coach.setCapacity(coachRequest.getCapacity());
        coach.setDesign(coachRequest.getDesign());

        if (coachRequest.getTrainId() != null) {
            coach.setTrain(
                    trainRepository.findById(coachRequest.getTrainId())
                            .orElseThrow(() ->
                                    new IllegalArgumentException("Train not found"))
            );
        } else {
            coach.setTrain(null);
        }

        return CoachMapper.toResponse(coachRepository.save(coach));
    }

    @Override
    public void deleteCoach(Long id) {

        if (!coachRepository.existsById(id)) {
            throw new IllegalArgumentException("Coach not found");
        }

        coachRepository.deleteById(id);
    }

    @Override
    public void deleteAllCoaches() {
        coachRepository.deleteAll();
    }


}
