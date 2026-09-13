package com.rare.userservice.service;


import com.rare.payload.response.auth.AuthResponse;
import com.rare.payload.response.auth.SignUpRequest;

import java.util.List;

public interface AuthService {
    AuthResponse login(String email, String password);
    AuthResponse signup(SignUpRequest userRequest);
    List<AuthResponse> signupBulk(List<SignUpRequest> userRequests);

}
