package com.rare.railwaycoreservice.mapper;

import com.rare.embeddable.Support;
import com.rare.payload.request.railway.RailwayRequest;
import com.rare.payload.response.railway.RailwayResponse;
import com.rare.railwaycoreservice.entity.Railway;
import com.rare.railwaycoreservice.repository.RailwayRepository;

public class RailwayMapper {

    public static Railway toEntity(RailwayRequest request,Long ownerId){
        if(request==null) return null;
        Railway railway = Railway.builder()
                .stationCode(request.getStationCode())
                .name(request.getName())
                .ownerId(ownerId)
                .alias(request.getAlias()).
                alliance(request.getAlliance())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .headquarterCityId(request.getHeadquarterCityId())
                .build();

        if(request.getSupportEmail()!=null || request.getSupportPhone()!=null || request.getSupportHours()!=null ){
            railway.setSupport(Support.builder().email(request.getSupportEmail()).phone(request.getSupportPhone()).hours(request.getSupportHours()).build());
        }
        return railway;
    }

    public static RailwayResponse toResponse(Railway railway){
        if(railway==null) return null;

        return RailwayResponse.builder()
                .id(railway.getId())
                .stationCode(railway.getStationCode())
                .name(railway.getName())
                .alias(railway.getAlias())
                .alliance(railway.getAlliance())
                .logoUrl(railway.getLogoUrl())
                .website(railway.getWebsite())
                .status(railway.getStatus())
                .ownerId(railway.getOwnerId())
                .createdAt(railway.getCreatedAt())
                .updatedAt(railway.getUpdatedAt())
                .support(railway.getSupport())
                .build();
    }

    public static Railway  updateRailway(Railway railway, RailwayRequest request) {
        if (railway == null || request == null) {
            return null;
        }

        if (request.getStationCode() != null && !request.getStationCode().isBlank()) {
            railway.setStationCode(request.getStationCode().trim().toUpperCase());
        }
        if (request.getName() != null && !request.getName().isBlank()) {
            railway.setName(request.getName().trim());
        }
        railway.setAlias(request.getAlias());
        if (request.getAlliance() != null && !request.getAlliance().isBlank()) {
            railway.setAlliance(request.getAlliance());
        }
        railway.setLogoUrl(request.getLogoUrl());
        railway.setWebsite(request.getWebsite());

        Support support = railway.getSupport();
        if (support == null) {
            support = new Support();
        }

        if (request.getSupportEmail() != null) {
            support.setEmail(request.getSupportEmail());
        }
        if (request.getSupportPhone() != null) {
            support.setPhone(request.getSupportPhone());
        }
        if (request.getSupportHours() != null) {
            support.setHours(request.getSupportHours());
        }
        railway.setSupport(support);
        return railway;
    }

}
