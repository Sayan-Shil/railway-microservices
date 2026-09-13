package com.rare.payload.dtos;

import com.rare.embeddable.Name;
import com.rare.enums.LoginMethod;
import com.rare.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Email is mandatory")
    private String email;

    private String password;

    private String phone;

    private Name name;

    private UserRole role;

    private LoginMethod loginMethod;

    private boolean enabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
}
