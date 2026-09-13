package com.rare.payload.request.train;

import com.rare.enums.CoachDesign;
import com.rare.enums.CoachType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachRequest {

    @NotBlank(message = "Coach Code is required")
    private String coachCode;

    @NotBlank(message = "Coach Type  is required")
    private CoachType type;

    @NotBlank(message = "Coach Design  is required ")
    private CoachDesign design;

    @NotBlank(message = "Capacity mention  is required")
    @Positive(message = "Capacity cannot be negatuive")
    private Integer capacity;

    private Long trainId;
}
