package com.rare.payload.response.auth;

import com.rare.embeddable.Name;
import com.rare.enums.LoginMethod;
import com.rare.enums.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    @Valid
    private Name name;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Pattern(
            regexp = "^\\+?[1-9]\\d{7,14}$",
            message = "Invalid phone number"
    )
    private String phone;

}
