package com.rare.userservice.service.auth;

import com.rare.userservice.entity.User;
import com.rare.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        // Fetch User with identifier --> Email or Phone
        User user = userRepository.findByEmailOrPhoneIgnoreCase(identifier.trim())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with identifier: " + identifier
                ));

        // 2. Build authorities list from role
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );

        // 3. Resolve username identifier
        String principal = (user.getEmail() != null && !user.getEmail().isBlank())
                ? user.getEmail()
                : user.getPhone();

        return org.springframework.security.core.userdetails.User.builder()
                .username(principal)
                .password(user.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
