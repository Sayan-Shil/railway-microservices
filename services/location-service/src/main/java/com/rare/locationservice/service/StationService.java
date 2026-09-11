package com.rare.locationservice.service;


import com.rare.payload.request.location.CityRequest;
import com.rare.payload.request.location.StationRequest;
import com.rare.payload.response.location.CityResponse;
import com.rare.payload.response.location.StationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StationService {

    StationResponse createStation(StationRequest stationRequest);
    List<StationResponse> createStations(List<StationRequest> stationRequests);
    StationResponse getStationById(Long id);
    StationResponse getStationByStationCode(String stationCode);
    Page<StationResponse> getAllStations(Pageable pageable);
    Page<StationResponse> getAllStationsByCityId(Long cityId, Pageable pageable);
    StationResponse updateStation(Long id,StationRequest stationRequest);
    void deleteStation(Long id);
    void deleteAllStations();
}
