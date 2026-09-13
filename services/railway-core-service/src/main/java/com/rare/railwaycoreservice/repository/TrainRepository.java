package com.rare.railwaycoreservice.repository;

import com.rare.railwaycoreservice.entity.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train,Long> {
    Page<Train> findByRailwayId(Long ownerId, Pageable pageable);
}
