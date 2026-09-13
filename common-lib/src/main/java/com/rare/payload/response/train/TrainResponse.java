package com.rare.payload.response.train;

import com.rare.enums.TrainStatus;
import com.rare.enums.TrainType;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainResponse {
    private Long id;
    private String code;
    private String name;
    private String model;
    private String manufacturer;
    private Integer yearOfManufacturer;
    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;
    private TrainStatus status;
    private TrainType trainType;
    private Integer operatingSpeedKmh;
    private Integer routeDistanceKm;
    private Boolean isAvailable;
    private Long railwayId;
    private Long currentRailwayId;
    private Integer totalCapacity;
    private Integer totalCoaches;
    List<CoachResponse> coaches = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;
}
