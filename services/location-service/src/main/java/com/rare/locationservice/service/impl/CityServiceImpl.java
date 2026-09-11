package com.rare.locationservice.service.impl;

import com.rare.locationservice.entity.City;
import com.rare.locationservice.mapper.CityMapper;
import com.rare.locationservice.repository.CityRepository;
import com.rare.locationservice.service.CityService;
import com.rare.payload.request.location.CityRequest;
import com.rare.payload.response.location.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;


    @Override
    public CityResponse createCity(CityRequest cityRequest) {
        if(cityRepository.existsCityByCityCode(cityRequest.getCityCode())){
            throw new IllegalArgumentException("City With Same Code Already Exists");
        }
        City city = CityMapper.toEntity(cityRequest);
        City res = cityRepository.save(city);
        return CityMapper.toResponse(res);
    }

    @Override
    public List<CityResponse> createCities(List<CityRequest> cityRequests) {

        List<City> cities = cityRequests.stream()
                .map(CityMapper::toEntity)
                .toList();

        return cityRepository.saveAll(cities)
                .stream()
                .map(CityMapper::toResponse)
                .toList();
    }

    @Override
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(()->new  IllegalArgumentException("City with the id doesn't exists") );
        return CityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest cityRequest) {
        City city = cityRepository.findById(id).orElseThrow(()->new  IllegalArgumentException("City with the id doesn't exists") );
        if(cityRepository.existsCityByCityCode(cityRequest.getCityCode())) {
            throw new IllegalArgumentException("City with the given ID is already exists!");
        }
        City result = cityRepository.save(CityMapper.updateEntity(city,cityRequest));
        return CityMapper.toResponse(result);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id).orElseThrow(()->new  IllegalArgumentException("City with the id doesn't exists") );
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword,pageable).map(CityMapper::toResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode,pageable).map(CityMapper::toResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsCityByCityCode(cityCode);
    }

    @Override
    public void deleteAllCities(){
        cityRepository.deleteAll();
    }
}
