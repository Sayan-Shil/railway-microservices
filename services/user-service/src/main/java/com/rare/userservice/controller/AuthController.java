package com.rare.userservice.controller;

import com.rare.payload.response.auth.AuthResponse;
import com.rare.payload.response.auth.LoginRequest;
import com.rare.payload.response.auth.SignUpRequest;
import com.rare.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Sign Up",
            description = "Sign Up With Mail/Phone Number"
    )
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @Operation(
            summary = "Bulk Sign Up",
            description = "Bulk Sign Up With Mail/Phone Number"
    )
    @PostMapping("/signup/bulk")
    public ResponseEntity<List<AuthResponse>> bulkSignUp(@Valid @RequestBody List<SignUpRequest> requests){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signupBulk(requests));
    }

    @Operation(
            summary = "Login",
            description = "LoginWith Mail/Phone Number"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
       if(request.getEmail()!=null){
           return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(request.getEmail(),request.getPassword()));
       }
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.login(request.getPhone(),request.getPassword()));
    }


}
