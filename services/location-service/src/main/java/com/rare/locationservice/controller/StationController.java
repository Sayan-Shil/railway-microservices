package com.rare.locationservice.controller;

import com.rare.locationservice.service.StationService;
import com.rare.payload.request.location.CityRequest;
import com.rare.payload.request.location.StationRequest;
import com.rare.payload.response.location.CityResponse;
import com.rare.payload.response.location.StationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;


    @Operation(
            summary = "Create Station",
            description = "Creates a new station"
    )
    @PostMapping
    public ResponseEntity<StationResponse> createStation(@Valid @RequestBody StationRequest stationRequest){
        StationResponse stationResponse = stationService.createStation(stationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(stationResponse);
    }

    @Operation(
            summary = "Create Stations",
            description = "Creates multiple stations"
    )
    @PostMapping("/bulk")
    public ResponseEntity<List<StationResponse>> createStations(
            @Valid @RequestBody List<@Valid StationRequest> stationRequests) {

        List<StationResponse> stationResponses =
                stationService.createStations(stationRequests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stationResponses);
    }

    @Operation(
            summary = "Get  Station By ID",
            description = "Retrieve All Stations With Station Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<StationResponse> getStationById(
            @PathVariable Long id
    ){
        StationResponse stationResponse = stationService.getStationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(stationResponse);
    }

    @Operation(
            summary = "Get All Stations",
            description = "Retrieve All Stations With Stations Pagewise"
    )
    @GetMapping
    public ResponseEntity<Page<StationResponse>> getAllStations(
            @RequestParam(defaultValue = "0") int  page,
            @RequestParam(defaultValue = "20") int  size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection) ,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.status(HttpStatus.OK).body(stationService.getAllStations(pageable));
    }

    @Operation(
            summary = "Update  Station By ID",
            description = "Update Station With Station Id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<StationResponse> updateStationById(
            @PathVariable Long id,
            @Valid @RequestBody StationRequest stationRequest
    ){
        StationResponse stationResponse = stationService.updateStation(id,stationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(stationResponse);
    }

    @Operation(
            summary = "Delete Station",
            description = "Deletes a station by its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete All Station",
            description = "Private API to delete all stations for testing purposes"
    )
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllStation() {
        stationService.deleteAllStations();
        return ResponseEntity.noContent().build();
    }

}
