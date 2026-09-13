package com.rare.userservice.controller;

import com.rare.payload.dtos.UserDto;
import com.rare.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {


    private final UserService userService;


    @Operation(
            summary = "Get User By Id",
            description = "Get User By Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@Param("id") Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(id));
    }


    @Operation(
            summary = "Get User Profile By Identifier",
            description = "Get User By Identifier via Email or Phone header"
    )
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader(value = "X-User-Email",required = false) String email,
            @RequestHeader(value = "X-User-Phone",required = false) String phone
    ) {
        boolean hasEmail = email != null && !email.isBlank();
        boolean hasPhone = phone != null && !phone.isBlank();

        if (!hasEmail && !hasPhone) {
            throw new IllegalArgumentException("Either email or phone number must be provided");
        }

        String identifier = hasEmail ? email.trim() : phone.trim();
        UserDto userDto = userService.getUserByIdentifier(identifier);

        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Get All",
            description = "Get All Users"
    )
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page ,
            @RequestParam(defaultValue = "10") int size ,
            @RequestParam(defaultValue = "email") String sortBy ,
            @RequestParam(defaultValue = "asc") String sortDirection

            ){
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserDto> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }


    @Operation(
            summary = "Delete All Users A",
            description = "Delete All users"
    )
    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAll(){
        userService.deleteAllUser();
        return ResponseEntity.noContent().build();
    }
}
