package com.rare.railwaycoreservice.service.impl;

import com.rare.enums.RailwayStatus;
import com.rare.payload.request.railway.RailwayRequest;
import com.rare.payload.response.railway.RailwayDropdownItem;
import com.rare.payload.response.railway.RailwayResponse;
import com.rare.railwaycoreservice.entity.Railway;
import com.rare.railwaycoreservice.mapper.RailwayMapper;
import com.rare.railwaycoreservice.repository.RailwayRepository;
import com.rare.railwaycoreservice.service.RailwayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RailwayServiceImpl implements RailwayService {

    private final RailwayRepository railwayRepository;

    @Override
    public RailwayResponse createRailway(RailwayRequest request,Long ownerId) {
        String stationCode = request.getStationCode();
        if(railwayRepository.existsByStationCode(stationCode)) throw new IllegalArgumentException("Railway with the same station code already exists!");

        Railway railway = RailwayMapper.toEntity(request,ownerId);
        Railway savedRailway =  railwayRepository.save(railway);
        return RailwayMapper.toResponse(savedRailway);
    }

    @Override
    public List<RailwayResponse> createBulkRailways(List<Map<Long,RailwayRequest>> requests) {
        List<Railway> railways = new ArrayList<>();

        for (Map<Long, RailwayRequest> requestMap : requests) {
            for (Map.Entry<Long, RailwayRequest> entry : requestMap.entrySet()) {
                Long ownerId = entry.getKey();
                RailwayRequest request = entry.getValue();

                String stationCode = request.getStationCode();

                if (railwayRepository.existsByStationCode(stationCode)) {
                    throw new IllegalArgumentException(
                            "Railway with station code " + stationCode + " already exists!"
                    );
                }
                Railway railway = RailwayMapper.toEntity(request, ownerId);
                railways.add(railway);
            }
        }

        List<Railway> savedRailways = railwayRepository.saveAll(railways);

        return savedRailways.stream()
                .map(RailwayMapper::toResponse)
                .toList();
    }

    @Override
    public RailwayResponse getRailwayByOwnerId(Long ownerId) {
        Railway railway = railwayRepository.findByOwnerId(ownerId).orElseThrow(()->new IllegalArgumentException("Station with same owner Id already exists"));
        return RailwayMapper.toResponse(railway);
    }

    @Override
    public RailwayResponse getRailwayById(Long id) {
        Railway railway = railwayRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Station with same  Id already exists"));
        return RailwayMapper.toResponse(railway);
    }

    @Override
    public Page<RailwayResponse> getAllRailways(Pageable pageable) {
        return railwayRepository.findAll(pageable).map(RailwayMapper::toResponse);
    }

    @Override
    public RailwayResponse updateRailway(Long id, RailwayRequest request) {
        Railway railway = railwayRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Station with same  Id already exists"));
       Railway savedRailway =  railwayRepository.save(RailwayMapper.updateRailway(railway,request));
       return RailwayMapper.toResponse(railway);
    }

    @Override
    public void deleteRailway(Long id, Long ownerId) {
        Railway railway = railwayRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Station with same  Id already exists"));
        if(Objects.equals(ownerId, railway.getOwnerId())){
            railwayRepository.delete(railway);
            return;
        }
        throw new IllegalArgumentException("Owner Id not matched! You need to be owner to delete this.");
    }

    @Override
    public void deleteAllRailways() {
        railwayRepository.deleteAll();
    }

    @Override
    public RailwayResponse changeRailwayStatus(long id, RailwayStatus status) {
        Railway railway = railwayRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Station with same  Id already exists"));
        railway.setStatus(status);
        Railway savedRailway = railwayRepository.save(railway);
        return RailwayMapper.toResponse(railway);
    }

    @Override
    public List<RailwayDropdownItem> getAirlineDropDown() {
        return railwayRepository.findByStatus(RailwayStatus.ACTIVE)
                .stream()
                .map(r -> RailwayDropdownItem.builder().id(r.getId()).stationCode(r.getStationCode()).name(r.getName()).logoUrl(r.getLogoUrl()).website(r.getWebsite()).build())
                .toList();

    }
}
