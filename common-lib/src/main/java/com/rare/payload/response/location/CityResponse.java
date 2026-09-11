package com.rare.payload.response.location;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rare.payload.embeddable.Address;
import com.rare.payload.embeddable.GeoCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityResponse {
    private Long id;
    private String name;
    private String cityCode;
    private String countryCode;
    private String countryName;
    private String regionCode;
    private String timeZoneOffset;
}
