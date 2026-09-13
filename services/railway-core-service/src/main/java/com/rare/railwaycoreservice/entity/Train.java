package com.rare.railwaycoreservice.entity;

import com.rare.enums.RailwayStatus;
import com.rare.enums.TrainStatus;
import com.rare.enums.TrainType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_code", unique = true, nullable = false)
    private String code;

    @Column(name = "train_name", unique = true, nullable = false)
    private String name;

    @Column(name = "train_model", nullable = false)
    private String model;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "year_of_manufacture", nullable = false)
    private Integer yearOfManufacturer;

    @Column(name = "registration_date",nullable = false)
    private LocalDate registrationDate;

    @Column(name = "next_maintenance_date",nullable = false)
    private LocalDate nextMaintenanceDate;


    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "status", nullable = false)
    private TrainStatus status = TrainStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "train_type", nullable = false)
    private TrainType trainType;

    @OneToMany(
            mappedBy = "train",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coach> coaches = new ArrayList<>();

    @Column(name = "operating_speed_kmph")
    private Integer operatingSpeedKmh;

    @Column(name = "route_distance_km",nullable = false)
    private Integer routeDistanceKm;

    @Builder.Default
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "railway_id")
    private Railway railway;

    @Column(name = "current_railway_id")
    private Long currentRailwayId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public int getTotalCapacity() {
        return coaches.stream()
                .mapToInt(Coach::getCapacity)
                .sum();
    }

    public int getTotalCoaches() {
        return coaches.size();
    }

    public void addCoach(Coach coach) {
        if (coach == null) {
            return;
        }

        coaches.add(coach);
        coach.setTrain(this);
    }

    public void removeCoach(Coach coach) {
        if (coach == null) {
            return;
        }

        coaches.remove(coach);
        coach.setTrain(null);
    }


    public boolean isTrainAvailable(){
        return TrainStatus.ACTIVE.equals(status) &&
                isAvailable;
    }

    public boolean requiresMaintenance(){
        return nextMaintenanceDate!=null && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(5));
    }



}
