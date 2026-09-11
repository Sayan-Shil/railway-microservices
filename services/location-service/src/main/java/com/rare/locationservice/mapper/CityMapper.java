package com.rare.locationservice.mapper;

import com.rare.locationservice.entity.City;
import com.rare.payload.request.location.CityRequest;
import com.rare.payload.response.location.CityResponse;

public class CityMapper {

    public static City toEntity(CityRequest cityRequest){
        if(cityRequest==null) return null;
        return City.builder()
                .name(cityRequest.getName())
                .cityCode(cityRequest.getCityCode())
                .countryCode(cityRequest.getCountryCode())
                .countryName(cityRequest.getCountryName())
                .regionCode(cityRequest.getRegionCode())
                .timeZoneOffset(cityRequest.getTimeZoneOffset())
                .build();
    }

    public static CityResponse toResponse(City city){
        if(city==null) return null;
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .regionCode(city.getRegionCode())
                .timeZoneOffset(city.getTimeZoneOffset())
                .build();
    }

    public static City updateEntity(City city , CityRequest cityRequest){

        if(cityRequest.getName()!=null) city.setName(cityRequest.getName().trim());
        if(cityRequest.getCityCode()!=null) city.setCityCode(cityRequest.getCityCode().toUpperCase().trim());
        if(cityRequest.getCountryCode()!=null) city.setCountryCode(cityRequest.getCountryCode().toUpperCase().trim());
        if(cityRequest.getCountryName()!=null) city.setCountryName(cityRequest.getCountryName().trim());
        if(cityRequest.getRegionCode()!=null) city.setRegionCode(cityRequest.getRegionCode().trim());
        if(cityRequest.getTimeZoneOffset()!=null) city.setTimeZoneOffset(cityRequest.getTimeZoneOffset().trim());

        return city;

    }
}
