package com.rare.railwaycoreservice.service;

import com.rare.payload.request.train.CoachRequest;
import com.rare.payload.response.train.CoachResponse;

import java.util.List;

public interface CoachService {
    CoachResponse createCoach(CoachRequest coachRequest);
    List<CoachResponse> createCoaches(List<CoachRequest> coachRequests);
    CoachResponse getCoachById(Long id);
    List<CoachResponse> getAllCoaches();
    CoachResponse updateCoach(Long id, CoachRequest coachRequest);
    void deleteCoach(Long id);
    void deleteAllCoaches();
}
