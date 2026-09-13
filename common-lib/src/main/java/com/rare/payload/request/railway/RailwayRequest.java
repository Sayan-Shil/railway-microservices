package com.rare.payload.request.railway;

import com.rare.embeddable.Support;
import com.rare.enums.RailwayStatus;
import com.rare.payload.dtos.UserDto;

import com.rare.payload.response.location.CityResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RailwayRequest {

    @NotBlank(message = "Station Code is must")
    @Size(max = 5)
    private String stationCode;

    @NotBlank(message = "Railway name cannot be blank")
    private String name;
    private String alias;
    @NotBlank(message = "alliance cannot be blank")
    private String alliance;

    private String logoUrl;
    private String website;

    private Long headquarterCityId;
    private String  supportEmail;
    private String  supportPhone;
    private String  supportHours;
}
