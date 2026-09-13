package com.rare.userservice.repository;

import com.rare.userservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmailIgnoreCase(String email);
    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByPhoneIgnoreCase(String phone);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:identifier) OR u.phone = :identifier")
    Optional<User> findByEmailOrPhoneIgnoreCase(@Param("identifier") String identifier);

}
