package com.rare.railwaycoreservice.mapper;

import com.rare.payload.request.train.TrainRequest;
import com.rare.payload.response.train.TrainResponse;
import com.rare.railwaycoreservice.entity.Train;

import java.util.ArrayList;

public class TrainMapper {
    public static Train toEntity(TrainRequest request) {

        if (request == null) {
            return null;
        }

        Train train = Train.builder()
                .code(request.getCode())
                .name(request.getName())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .yearOfManufacturer(request.getYearOfManufacturer())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .trainType(request.getTrainType())
                .operatingSpeedKmh(request.getOperatingSpeedKmh())
                .routeDistanceKm(request.getRouteDistanceKm())
                .currentRailwayId(request.getCurrentRailwayId())
                .build();
        if (request.getCoaches() != null) {
            request.getCoaches()
                    .stream()
                    .map(CoachMapper::toEntity)
                    .forEach(train::addCoach);
        }
        return train;
    }

    public static TrainResponse toResponse(Train train) {

        if (train == null) {
            return null;
        }

        return TrainResponse.builder()
                .id(train.getId())
                .code(train.getCode())
                .name(train.getName())
                .model(train.getModel())
                .manufacturer(train.getManufacturer())
                .yearOfManufacturer(train.getYearOfManufacturer())
                .registrationDate(train.getRegistrationDate())
                .nextMaintenanceDate(train.getNextMaintenanceDate())
                .status(train.getStatus())
                .trainType(train.getTrainType())
                .operatingSpeedKmh(train.getOperatingSpeedKmh())
                .routeDistanceKm(train.getRouteDistanceKm())
                .isAvailable(train.getIsAvailable())
                .railwayId(
                        train.getRailway() != null
                                ? train.getRailway().getId()
                                : null
                )
                .currentRailwayId(train.getCurrentRailwayId())
                .totalCapacity(train.getTotalCapacity())
                .totalCoaches(train.getTotalCoaches())
                .coaches(
                        train.getCoaches() == null
                                ? new ArrayList<>()
                                : train.getCoaches()
                                .stream()
                                .map(CoachMapper::toResponse)
                                .toList()
                )
                .createdAt(train.getCreatedAt())
                .updatedAt(train.getUpdatedAt())
                .build();
    }
}
