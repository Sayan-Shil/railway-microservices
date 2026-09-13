package com.rare.railwaycoreservice.controller;

import com.rare.payload.request.train.CoachRequest;
import com.rare.payload.response.train.CoachResponse;
import com.rare.railwaycoreservice.service.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@RequiredArgsConstructor
@Tag(name = "Coach", description = "Coach management APIs")
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Create Coach")
    @PostMapping
    public ResponseEntity<CoachResponse> createCoach(
            @Valid @RequestBody CoachRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(coachService.createCoach(request));
    }

    @Operation(summary = "Create Bulk Coaches")
    @PostMapping("/bulk")
    public ResponseEntity<List<CoachResponse>> createBulkCoaches(
            @Valid @RequestBody List<CoachRequest> requests) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(coachService.createCoaches(requests));
    }

    @Operation(summary = "Get Coach By ID")
    @GetMapping("/{id}")
    public ResponseEntity<CoachResponse> getCoachById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                coachService.getCoachById(id)
        );
    }

    @Operation(summary = "Get All Coaches")
    @GetMapping
    public ResponseEntity<List<CoachResponse>> getAllCoaches() {

        return ResponseEntity.ok(
                coachService.getAllCoaches()
        );
    }

    @Operation(summary = "Update Coach")
    @PutMapping("/{id}")
    public ResponseEntity<CoachResponse> updateCoach(
            @PathVariable Long id,
            @Valid @RequestBody CoachRequest request) {

        return ResponseEntity.ok(
                coachService.updateCoach(id, request)
        );
    }

    @Operation(summary = "Delete Coach")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoach(
            @PathVariable Long id) {

        coachService.deleteCoach(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete All Coaches")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllCoaches() {

        coachService.deleteAllCoaches();

        return ResponseEntity.noContent().build();
    }
}