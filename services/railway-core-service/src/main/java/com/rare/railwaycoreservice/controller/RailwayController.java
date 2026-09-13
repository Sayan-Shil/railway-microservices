package com.rare.railwaycoreservice.controller;

import com.rare.enums.RailwayStatus;
import com.rare.payload.request.railway.RailwayRequest;
import com.rare.payload.response.railway.RailwayDropdownItem;
import com.rare.payload.response.railway.RailwayResponse;
import com.rare.railwaycoreservice.service.RailwayService;
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
@RequiredArgsConstructor
@RequestMapping("/api/railway")
@Tag(name = "Railway", description = "Railway Service APIs")
public class RailwayController {

    private final RailwayService railwayService;

    @Operation(
            summary = "Create railway",
            description = "Creates a new railway using the provided railway details and assigns it to the authenticated user."
    )
    @PostMapping
    public ResponseEntity<RailwayResponse> createRailway(
            @Valid @RequestBody RailwayRequest request,
            @RequestHeader("X-USER-ID") Long  userId
            ){
        RailwayResponse response =  railwayService.createRailway(request,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Create railways in bulk",
            description = "Creates multiple railways in a single request."
    )
    @PostMapping("/bulk")
    public ResponseEntity<List<RailwayResponse>> createBulkRailways(
            @Valid @RequestBody List<Map<Long,RailwayRequest>> requests
    ){
        List<RailwayResponse> responses =  railwayService.createBulkRailways(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @Operation(
            summary = "Get railway by owner ID",
            description = "Retrieves the railway owned by the authenticated user."
    )
    @GetMapping("/admin")
    public ResponseEntity<RailwayResponse> getRailwayByOwnerId(
            @RequestHeader("X-USER-ID") Long  userId
    ){
        return ResponseEntity.ok(railwayService.getRailwayByOwnerId(userId));
    }

    @Operation(
            summary = "Get railway by ID",
            description = "Retrieves a railway using its unique ID."
    )
    @GetMapping("{id}")
    public ResponseEntity<RailwayResponse> getRailwayById(
            @PathVariable Long  id
    ){
        return ResponseEntity.ok(railwayService.getRailwayById(id));
    }

    @Operation(
            summary = "Get all railways",
            description = "Retrieves all railways using pagination and sorting."
    )
    @GetMapping
    public ResponseEntity<Page<RailwayResponse>> getAllRailways(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ){
        sortDirection=  sortDirection.equalsIgnoreCase("asc") ? "asc" : "desc";
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection) ,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.status(HttpStatus.OK).body(railwayService.getAllRailways(pageable));
    }

    @Operation(
            summary = "Get railway dropdown",
            description = "Retrieves a list of railways containing the information required for dropdown selection."
    )
    @GetMapping("/dropdown")
    public ResponseEntity<List<RailwayDropdownItem>> getRailwayDropDown(){
        return ResponseEntity.ok(railwayService.getAirlineDropDown());
    }

    @Operation(
            summary = "Update railway",
            description = "Updates the railway details belonging to the authenticated user."
    )
    @PutMapping
    public ResponseEntity<RailwayResponse> updateRailway(
            @Valid @RequestBody RailwayRequest request,
            @RequestHeader("X-USER-ID") Long  userId
    ){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(railwayService.updateRailway(userId,request));
    }

    @Operation(
            summary = "Delete railway",
            description = "Deletes a railway by its ID after verifying the authenticated user."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRailway(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long  userId
    ){
        railwayService.deleteRailway(id,userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete all railways",
            description = "Deletes all railways from the system."
    )
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllRailways(){
        railwayService.deleteAllRailways();
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Approve railway",
            description = "Approves a railway and changes its status to ACTIVE."
    )
    @PostMapping("{id}/approve")
    public ResponseEntity<RailwayResponse> approveAirline(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(railwayService.changeRailwayStatus(id, RailwayStatus.ACTIVE));
    }

    @Operation(
            summary = "Suspend railway",
            description = "Suspends a railway and changes its status to SUSPENDED."
    )
    @PostMapping("{id}/suspend")
    public ResponseEntity<RailwayResponse> suspendAirline(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(railwayService.changeRailwayStatus(id, RailwayStatus.SUSPENDED));
    }

    @Operation(
            summary = "Close railway",
            description = "Closes a railway and changes its status to CLOSED."
    )
    @PostMapping("{id}/close")
    public ResponseEntity<RailwayResponse> closeAirline(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(railwayService.changeRailwayStatus(id, RailwayStatus.CLOSED));
    }

    @Operation(
            summary = "Disable railway",
            description = "Disables a railway and changes its status to INACTIVE."
    )
    @PostMapping("{id}/disable")
    public ResponseEntity<RailwayResponse> disableAirline(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(railwayService.changeRailwayStatus(id, RailwayStatus.INACTIVE));
    }




}
