package com.rare.payload.response.railway;

import com.rare.embeddable.Support;
import com.rare.enums.RailwayStatus;
import com.rare.payload.dtos.UserDto;

import com.rare.payload.response.location.CityResponse;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RailwayResponse {

    private Long id;
    private String stationCode;

    private String name;
    private String alias;
    private String alliance;

    private String logoUrl;
    private String website;

    private RailwayStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    private UserDto owner;
    private Long ownerId;
    private Long updatedById;

    private CityResponse headquarterCity;
    private Support support;
}
