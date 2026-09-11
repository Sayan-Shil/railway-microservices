package com.rare.payload.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoCode {
    private Double latitude;
    private Double longitude;
}
