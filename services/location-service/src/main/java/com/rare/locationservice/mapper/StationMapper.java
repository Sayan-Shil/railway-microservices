package com.rare.locationservice.mapper;

import com.rare.locationservice.entity.City;
import com.rare.locationservice.entity.Station;
import com.rare.payload.request.location.StationRequest;
import com.rare.payload.response.location.StationResponse;

public class StationMapper {

    public static Station toEntity(StationRequest stationRequest){
        if(stationRequest==null) return null;
        return Station.builder()
                .name(stationRequest.getName())
                .stationCode(stationRequest.getStationCode())
                .timeZone(stationRequest.getTimeZone())
                .address(stationRequest.getAddress())
                .geoCode(stationRequest.getGeoCode())
                .build();
    }
    public static StationResponse toResponse(Station station){
        if(station==null) return null;
        return StationResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .detailedName(station.getDetailedName())
                .stationCode(station.getStationCode())
                .timeZone(station.getTimeZone())
                .address(station.getAddress())
                .geoCode(station.getGeoCode())
                .cityResponse(CityMapper.toResponse(station.getCity()))
                .build();
    }

    public static Station updateEntity(Station station, StationRequest stationRequest) {

        if (stationRequest.getStationCode() != null)
            station.setStationCode(stationRequest.getStationCode().toUpperCase().trim());

        if (stationRequest.getName() != null)
            station.setName(stationRequest.getName().trim());

        if (stationRequest.getAddress() != null)
            station.setAddress(stationRequest.getAddress());

        if (stationRequest.getGeoCode() != null)
            station.setGeoCode(stationRequest.getGeoCode());

        if (stationRequest.getTimeZone() != null)
            station.setTimeZone(stationRequest.getTimeZone().trim());

        return station;
    }

}
