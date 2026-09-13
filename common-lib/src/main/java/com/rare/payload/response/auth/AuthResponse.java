package com.rare.payload.response.auth;

import com.rare.payload.dtos.UserDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private  String jwtToken;
    private UserDto userDto;
    private String title;
    private String message;
}
