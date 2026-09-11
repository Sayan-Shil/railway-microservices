package com.rare.payload.response.location;

import com.rare.payload.embeddable.Address;
import com.rare.payload.embeddable.GeoCode;
import lombok.*;

import java.time.ZoneId;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationResponse {
    private Long id;
    private String stationCode;
    private String name;
    private String detailedName;
    private Address address;
    private GeoCode geoCode;
    private String cityCode;
    private String timeZone;
    private CityResponse cityResponse;
}
