package com.rare.payload.request.location;

import com.rare.embeddable.Address;
import com.rare.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationRequest {
    @NotBlank(message = "Station Code is mandatory")
    @Size(max = 5)
    private String stationCode;
    @NotBlank(message = "Station Name Cannot Be Blank")
    private String name;

    @Valid
    private Address address;

    @Valid
    private GeoCode geoCode;

    private String timeZone;

    private Long cityId;
}
