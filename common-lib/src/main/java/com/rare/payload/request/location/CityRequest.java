package com.rare.payload.request.location;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequest {
    @NotBlank(message = "City Must Have Name")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "City Code is required")
    @Size(max = 10)
    private String cityCode;

    @NotBlank(message = "Country Code is required")
    @Size(max = 5)
    private String countryCode;

    @NotBlank(message = "Country must have a name ")
    @Size(max = 100)
    private String countryName;

    @NotBlank(message = "Region Code is required")
    @Size(max = 10)
    private String regionCode;

    @NotBlank(message = "Time Zone Offset  is required")
    @Size(max = 10)
    private String timeZoneOffset;
}
