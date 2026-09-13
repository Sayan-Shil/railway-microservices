package com.rare.payload.response.railway;

import com.rare.embeddable.Support;
import com.rare.enums.RailwayStatus;
import com.rare.payload.dtos.UserDto;
import com.rare.payload.response.location.CityResponse;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RailwayDropdownItem {
    private Long id;
    private String stationCode;
    private String name;
    private String logoUrl;
    private String website;
    private String country;
}
