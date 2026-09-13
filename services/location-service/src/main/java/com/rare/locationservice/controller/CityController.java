package com.rare.locationservice.controller;

import com.rare.locationservice.entity.City;
import com.rare.locationservice.service.CityService;
import com.rare.payload.response.location.CityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rare.payload.request.location.CityRequest;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "City", description = "City management APIs")
public class CityController {
    private final CityService cityService;


    @Operation(
            summary = "Create City",
            description = "Creates a new city"
    )
    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest){
        CityResponse cityResponse = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse);
    }

    @Operation(
            summary = "Create Cities",
            description = "Creates multiple cities"
    )
    @PostMapping("/bulk")
    public ResponseEntity<List<CityResponse>> createCities(
            @Valid @RequestBody List<@Valid CityRequest> cityRequests) {

        List<CityResponse> cityResponses =
                cityService.createCities(cityRequests);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cityResponses);
    }

    @Operation(
            summary = "Get  City By ID",
            description = "Retrieve All Cities With City Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(
            @PathVariable Long id
    ){
        CityResponse cityResponse = cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.OK).body(cityResponse);
    }


    @Operation(
            summary = "Get All Cities",
            description = "Retrieve All Cities With Cities Pagewise"
    )
    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(
            @RequestParam(defaultValue = "0") int  page,
            @RequestParam(defaultValue = "20") int  size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection) ,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllCities(pageable));
    }


    @Operation(
            summary = "Search For Cities",
            description = "Retrieve All Cities With Cities Pagewise"
    )
    @GetMapping("/search/{keyword}")
    public ResponseEntity<Page<CityResponse>> searchForCity(
            @RequestParam(defaultValue = "0") int  page,
            @RequestParam(defaultValue = "20") int  size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @PathVariable String keyword
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection) ,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.searchCities(keyword,pageable));
    }

    @Operation(
            summary = "Get All Cities by Country Code",
            description = "Retrieve All Cities By Country Code Pagewise"
    )
    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
            @RequestParam(defaultValue = "0") int  page,
            @RequestParam(defaultValue = "20") int  size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @PathVariable String countryCode
    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection) ,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.status(HttpStatus.OK)
                .body(cityService.getCitiesByCountryCode(countryCode,pageable));
    }


    @Operation(
            summary = "Update  City By ID",
            description = "Update City With City Id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCityById(
            @PathVariable Long id,
            @Valid @RequestBody CityRequest cityRequest
    ){
        CityResponse cityResponse = cityService.updateCity(id,cityRequest);
        return ResponseEntity.status(HttpStatus.OK).body(cityResponse);
    }

    @Operation(
            summary = "Check For City",
            description = "Check If City exists"
    )
    @GetMapping("exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode){
        return ResponseEntity.ok(cityService.cityExists(cityCode));
    }

    @Operation(
            summary = "Delete City",
            description = "Deletes a city by its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete All Cities",
            description = "Private API to delete all cities for testing purposes"
    )
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCities() {
        cityService.deleteAllCities();
        return ResponseEntity.noContent().build();
    }

}
