package com.rare.railwaycoreservice.service;

import com.rare.payload.request.train.CoachRequest;
import com.rare.payload.request.train.TrainRequest;
import com.rare.payload.response.ApiResponse;
import com.rare.payload.response.train.TrainResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TrainService {
    TrainResponse createTrain(TrainRequest request,Long ownerId);
    List<TrainResponse> createBulkTrains(List<Map<Long,TrainRequest>> requests);
    TrainResponse getTrainById(Long id);
    Page<TrainResponse> getAllTrainsByOwnerId(Long ownerId, Pageable pageable);
    TrainResponse updateTrain(TrainRequest request,Long id);
    void deleteTrain(Long id, Long ownerId);
    void deleteAllTrain();

    ApiResponse<TrainResponse> addCoach(Long id, Long coachId );
    ApiResponse<TrainResponse> removeCoach(Long id, Long coachId);

}
