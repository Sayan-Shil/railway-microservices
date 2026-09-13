package com.rare.railwaycoreservice.service;

import com.rare.enums.RailwayStatus;
import com.rare.payload.request.railway.RailwayRequest;
import com.rare.payload.response.railway.RailwayDropdownItem;
import com.rare.payload.response.railway.RailwayResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RailwayService {
    RailwayResponse createRailway(RailwayRequest request,Long ownerId);
    List<RailwayResponse> createBulkRailways(List<Map<Long,RailwayRequest>> requests);
    RailwayResponse getRailwayByOwnerId(Long ownerId);
    RailwayResponse getRailwayById(Long id);
    Page<RailwayResponse> getAllRailways(Pageable pageable);
    RailwayResponse updateRailway(Long id,RailwayRequest request);
    void deleteRailway(Long id,Long ownerId);
    void deleteAllRailways();

    RailwayResponse changeRailwayStatus(long id, RailwayStatus status);
    List<RailwayDropdownItem> getAirlineDropDown();
}
