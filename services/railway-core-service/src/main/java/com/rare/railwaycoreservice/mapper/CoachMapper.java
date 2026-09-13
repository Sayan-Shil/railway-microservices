package com.rare.railwaycoreservice.mapper;

import com.rare.payload.request.train.CoachRequest;
import com.rare.payload.response.train.CoachResponse;
import com.rare.railwaycoreservice.entity.Coach;
import lombok.*;

public class CoachMapper {
    public static CoachResponse toResponse(Coach coach) {
        return CoachResponse.builder()
                .id(coach.getId())
                .coachCode(coach.getCoachCode())
                .type(coach.getType())
                .capacity(coach.getCapacity())
                .design(coach.getDesign())
                .trainId(
                        coach.getTrain() != null
                                ? coach.getTrain().getId()
                                : null
                )
                .build();
    }

    public static Coach toEntity(CoachRequest request) {

        if (request == null) {
            return null;
        }

        return Coach.builder()
                .coachCode(request.getCoachCode())
                .type(request.getType())
                .capacity(request.getCapacity())
                .design(request.getDesign())
                .build();
    }
}
