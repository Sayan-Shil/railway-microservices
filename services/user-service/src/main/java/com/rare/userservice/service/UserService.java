package com.rare.userservice.service;

import com.rare.payload.dtos.UserDto;
import com.rare.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto getUserByIdentifier(String identifier);
    UserDto getUserById(Long id);
    Page<UserDto> getAllUsers(Pageable pageable);
    void deleteAllUser();
}
