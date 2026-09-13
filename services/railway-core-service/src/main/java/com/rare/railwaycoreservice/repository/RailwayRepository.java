package com.rare.railwaycoreservice.repository;

import com.rare.enums.RailwayStatus;
import com.rare.railwaycoreservice.entity.Railway;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RailwayRepository extends JpaRepository<Railway,Long> {

    Optional<Railway> findByOwnerId(Long ownerId);
    Boolean existsByStationCode(String stationCode);

    List<Railway> findByStatus(RailwayStatus status);
}
