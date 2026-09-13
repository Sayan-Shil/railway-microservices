package com.rare.userservice.service.impl;

import com.rare.embeddable.Name;
import com.rare.enums.UserRole;
import com.rare.payload.dtos.UserDto;
import com.rare.payload.response.auth.AuthResponse;
import com.rare.payload.response.auth.SignUpRequest;
import com.rare.userservice.config.JwtProvider;
import com.rare.userservice.config.SecurityConfig;
import com.rare.userservice.entity.User;
import com.rare.userservice.mapper.UserMapper;
import com.rare.userservice.repository.UserRepository;
import com.rare.userservice.service.AuthService;
import com.rare.userservice.service.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;


    @Override
    public AuthResponse login(String emailOrPhone, String password) {
        if (emailOrPhone == null || emailOrPhone.isBlank()) {
            throw new IllegalArgumentException("Email or phone number is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        String identifier = emailOrPhone.trim();
        Authentication authentication = authenticate(identifier,password);
        User user = userRepository.findByEmailOrPhoneIgnoreCase(identifier).orElseThrow(()->new RuntimeException("Something went wrong!"));
    user.setLastLogin(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        String jwt = jwtProvider.generateToken(authentication,user.getId());
        return AuthResponse.builder()
                .jwtToken(jwt)
                .userDto(UserMapper.toDto(savedUser))
                .title("Welcome " + savedUser.detailedName() + " to Rare Railway Management System!")
                .message("Registration Successful !")
                .build();
    }

    private Authentication authenticate(String identifier, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(identifier);
      if(!passwordEncoder.matches(password,userDetails.getPassword())){
          throw new IllegalArgumentException("Password not matched ! Please Sign in with correct password");
      }
      return new UsernamePasswordAuthenticationToken(
              userDetails.getUsername(),
              null,
              userDetails.getAuthorities()
      );
    }

    @Override
    @Transactional
    public AuthResponse signup(SignUpRequest userRequest) {
        String email = userRequest.getEmail();
        String phone = userRequest.getPhone();

        // 1. Validate contact info presence
        boolean hasEmail = email != null && !email.isBlank();
        boolean hasPhone = phone != null && !phone.isBlank();

        if (!hasEmail && !hasPhone) {
            throw new IllegalArgumentException("Either email or phone number is required for signup");
        }

        // 2. Validate uniqueness
        if (hasEmail && userRepository.existsByEmailIgnoreCase(email.trim())) {
            throw new IllegalArgumentException("Email already exists...");
        }

        if (hasPhone && userRepository.existsByPhoneIgnoreCase(phone.trim())) {
            throw new IllegalArgumentException("Phone number already exists..");
        }

        // 3. Restrict administrative self-registration
//        if (userRequest.getRole() == UserRole.ROLE_SYSTEM_ADMIN
//                || userRequest.getRole() == UserRole.ROLE_STATION_OWNER) {
//            throw new IllegalArgumentException("You cannot sign up with the role of System Admin or Station Owner!");
//        }


        // 4. Build and persist user
        Name name = userRequest.getName();
        User user = User.builder()
                .email(hasEmail ? email.trim() : null)
                .phone(hasPhone ? phone.trim() : null)
                .name(name)
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .lastLogin(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        // 5. Creating Authentication
        String principal = (savedUser.getEmail() != null && !savedUser.getEmail().isBlank())
                ? savedUser.getEmail()
                : savedUser.getPhone();

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(savedUser.getRole().name())
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                authorities
        );

        // 6. Generate JWT and build response
        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());

        String displayName = (savedUser.detailedName() != null && !savedUser.detailedName().isBlank())
                ? savedUser.detailedName().trim()
                : principal;

        return AuthResponse.builder()
                .jwtToken(jwt)
                .userDto(UserMapper.toDto(savedUser))
                .title("Welcome " + displayName + " to Rare Railway Management System!")
                .message("Registration Successful !")
                .build();
    }

    @Override
    @Transactional
    public List<AuthResponse> signupBulk(List<SignUpRequest> userRequests) {
        if (userRequests == null || userRequests.isEmpty()) {
            return Collections.emptyList();
        }

        //Prevents Duplicates
        Set<String> emailSet = new HashSet<>();
        Set<String> phoneSet = new HashSet<>();

        for (SignUpRequest req : userRequests) {
            if (req.getEmail() != null && !req.getEmail().isBlank()) {
                String cleanEmail = req.getEmail().trim().toLowerCase();
                if (!emailSet.add(cleanEmail)) {
                    throw new IllegalArgumentException("Duplicate email found in batch request: " + req.getEmail());
                }
            }
            if (req.getPhone() != null && !req.getPhone().isBlank()) {
                String cleanPhone = req.getPhone().trim();
                if (!phoneSet.add(cleanPhone)) {
                    throw new IllegalArgumentException("Duplicate phone number found in batch request: " + req.getPhone());
                }
            }
        }
       return   userRequests.stream().map(this::signup).toList();
    }



}
