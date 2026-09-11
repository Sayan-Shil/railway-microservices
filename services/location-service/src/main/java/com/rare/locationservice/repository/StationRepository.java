package com.rare.locationservice.repository;

import com.rare.locationservice.entity.Station;
import com.rare.payload.response.location.StationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station,Long> {

    Optional<Station> findByStationCode(String stationCode);
    Page<Station> findByCityId(Long cityId, Pageable pageable);
    boolean existsByStationCode(String stationCode);
}
