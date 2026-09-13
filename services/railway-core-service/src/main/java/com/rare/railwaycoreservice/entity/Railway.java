package com.rare.railwaycoreservice.entity;

import com.rare.embeddable.Support;
import com.rare.enums.RailwayStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Railway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String stationCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    private String alias;
    private String alliance;

    @Column(nullable = false)
    private String logoUrl;
    @Column(nullable = false)
    private String website;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RailwayStatus status = RailwayStatus.ACTIVE;

    private Long headquarterCityId;

    @Embedded
    private Support support;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;


}
