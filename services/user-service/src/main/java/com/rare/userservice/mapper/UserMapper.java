package com.rare.userservice.mapper;

import com.rare.payload.dtos.UserDto;
import com.rare.userservice.entity.User;

public class UserMapper {

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .loginMethod(user.getLoginMethod())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
