package com.rare.railwaycoreservice.repository;

import com.rare.railwaycoreservice.entity.Coach;
import com.rare.railwaycoreservice.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach,Long> {

    Optional<Coach> findByCoachCode(String coachCode);
    boolean existsByCoachCode(String coachCode);
}
