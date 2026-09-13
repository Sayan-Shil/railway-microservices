package com.rare.railwaycoreservice.entity;


import com.rare.enums.CoachDesign;
import com.rare.enums.CoachType;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String coachCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoachType type;


    @Enumerated(EnumType.STRING)
    private CoachDesign design;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;
}
