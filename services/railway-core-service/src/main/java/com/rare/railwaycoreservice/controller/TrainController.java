package com.rare.railwaycoreservice.controller;

import com.rare.payload.request.train.TrainRequest;
import com.rare.payload.response.train.TrainResponse;
import com.rare.railwaycoreservice.service.TrainService;
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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
@Tag(name = "Train", description = "Train management APIs")
public class TrainController {

    private final TrainService trainService;

    @Operation(summary = "Create Train")
    @PostMapping
    public ResponseEntity<TrainResponse> createTrain(
            @Valid @RequestBody TrainRequest request,
            @RequestHeader("X-USER-ID") Long ownerId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trainService.createTrain(request, ownerId));
    }

    @Operation(summary = "Create Bulk Trains")
    @PostMapping("/bulk")
    public ResponseEntity<List<TrainResponse>> createBulkTrains(
            @Valid @RequestBody List<Map<Long, TrainRequest>> requests) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(trainService.createBulkTrains(requests));
    }

    @Operation(summary = "Get Train By ID")
    @GetMapping("/{id}")
    public ResponseEntity<TrainResponse> getTrainById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                trainService.getTrainById(id)
        );
    }

    @Operation(summary = "Get All Trains By Owner")
    @GetMapping
    public ResponseEntity<Page<TrainResponse>> getAllTrainsByOwnerId(
            @RequestParam Long ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Sort sort = Sort.by(
                Sort.Direction.fromString(sortDirection),
                sortBy
        );

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(
                trainService.getAllTrainsByOwnerId(ownerId, pageable)
        );
    }

    @Operation(summary = "Update Train")
    @PutMapping("/{id}")
    public ResponseEntity<TrainResponse> updateTrain(
            @PathVariable Long id,
            @Valid @RequestBody TrainRequest request) {

        return ResponseEntity.ok(
                trainService.updateTrain(request, id)
        );
    }

    @Operation(summary = "Delete Train")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long ownerId ) {

        trainService.deleteTrain(id, ownerId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete All Trains")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllTrains() {
        trainService.deleteAllTrain();
        return ResponseEntity.noContent().build();
    }
}