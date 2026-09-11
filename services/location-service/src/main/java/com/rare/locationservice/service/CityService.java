package com.rare.locationservice.service;

import com.rare.payload.response.location.CityResponse;
import com.rare.payload.request.location.CityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {

    CityResponse createCity(CityRequest cityRequest);
    List<CityResponse> createCities(List<CityRequest> cityRequests);
    CityResponse getCityById(Long id);
    CityResponse updateCity(Long id, CityRequest cityRequest);
    void deleteCity(Long id);
    Page<CityResponse> getAllCities(Pageable pageable);
    Page<CityResponse> searchCities(String keyword, Pageable pageable);
    Page<CityResponse> getCitiesByCountryCode(String countryCode,Pageable pageable);
    boolean cityExists(String cityCode);
    void deleteAllCities();
}
