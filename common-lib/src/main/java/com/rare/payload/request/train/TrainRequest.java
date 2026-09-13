package com.rare.payload.request.train;

import com.rare.enums.TrainStatus;
import com.rare.enums.TrainType;
import com.rare.payload.request.railway.RailwayRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class TrainRequest {

    @NotBlank(message = "Train Code is required")
    private String code;

    @NotBlank(message = "Train Name is required")
    private String name;
    @NotBlank(message = "Train Model is required")
    private String model;
    @NotBlank(message = "Please  mention Train Manufacturer")
    private String manufacturer;

    @NotBlank(message = "Year of Manufacture must be mentioned")
    @Positive(message = "Year cannot be negative")
    private Integer yearOfManufacturer;

    @NotBlank(message = "Registration Date required")
    @Positive(message = "Year cannot be negative")
    private LocalDate registrationDate;

    @NotBlank(message = "Next Maintenance Date required")
    @Positive(message = "Year cannot be negative")
    private LocalDate nextMaintenanceDate;

    private TrainType trainType;
    private List<CoachRequest> coaches = new ArrayList<>();
    private Integer operatingSpeedKmh;
    private Long currentRailwayId;
    private Integer routeDistanceKm;

}
