package com.rare.userservice.service.impl;

import com.rare.payload.dtos.UserDto;
import com.rare.userservice.entity.User;
import com.rare.userservice.mapper.UserMapper;
import com.rare.userservice.repository.UserRepository;
import com.rare.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserByIdentifier(String identifier) {
        User user  =  userRepository.findByEmailOrPhoneIgnoreCase(identifier).orElseThrow(()-> new IllegalArgumentException("User not of found with this identifier"));
        return UserMapper.toDto(user);
    }


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("User not of found with this id"));
        return UserMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toDto);
    }

    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }
}
