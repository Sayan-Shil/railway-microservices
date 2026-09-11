package com.rare.locationservice.service.impl;


import com.rare.locationservice.entity.Station;
import com.rare.locationservice.mapper.StationMapper;
import com.rare.locationservice.repository.StationRepository;

import com.rare.locationservice.service.StationService;
import com.rare.payload.request.location.StationRequest;
import com.rare.payload.response.location.StationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public StationResponse createStation(StationRequest stationRequest) {
        if(stationRepository.existsByStationCode(stationRequest.getStationCode())){
            throw new IllegalArgumentException("Station With Same Station Code Already Exists");
        }
        Station station = StationMapper.toEntity(stationRequest);
        Station res = stationRepository.save(station);
        return StationMapper.toResponse(res);
    }

    @Override
    public List<StationResponse> createStations(List<StationRequest> stationRequests) {

        List<Station> stations = stationRequests.stream()
                .map(StationMapper::toEntity)
                .toList();

        return stationRepository.saveAll(stations)
                .stream()
                .map(StationMapper::toResponse)
                .toList();
    }

    @Override
    public StationResponse getStationById(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(()->new  IllegalArgumentException("Station with the id doesn't exists") );
        return StationMapper.toResponse(station);
    }

    @Override
    public StationResponse getStationByStationCode(String stationCode) {
        Station station = stationRepository.findByStationCode(stationCode).orElseThrow(()->new  IllegalArgumentException("Station with the id doesn't exists") );
        return StationMapper.toResponse(station);
    }

    @Override
    public Page<StationResponse> getAllStations(Pageable pageable) {
        return stationRepository.findAll(pageable).map(StationMapper::toResponse);
    }

    @Override
    public Page<StationResponse> getAllStationsByCityId(Long cityId, Pageable pageable) {
        return stationRepository.findByCityId(cityId,pageable).map(StationMapper::toResponse);
    }

    @Override
    public StationResponse updateStation(Long id, StationRequest stationRequest) {
        Station station = stationRepository.findById(id).orElseThrow(()->new  IllegalArgumentException("Station with the id doesn't exists") );
        if(stationRepository.existsByStationCode(stationRequest.getStationCode())) {
            throw new IllegalArgumentException("Station with the given Station ID is already exists!");
        }
        Station res = stationRepository.save(StationMapper.updateEntity(station,stationRequest));
        return StationMapper.toResponse(res);
    }

    @Override
    public void deleteStation(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(()->new  IllegalArgumentException("Station with the id doesn't exists") );
        stationRepository.delete(station);
    }

    @Override
    public void deleteAllStations(){
        stationRepository.deleteAll();
    }


}
