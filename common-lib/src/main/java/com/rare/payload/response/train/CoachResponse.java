package com.rare.payload.response.train;

import com.rare.enums.CoachDesign;
import com.rare.enums.CoachType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoachResponse {
    private Long id;
    private String coachCode;
    private CoachType type;
    private CoachDesign design;
    private Integer capacity;
    private long trainId;
}
