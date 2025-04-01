package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Venue;
import com.example.demo.service.VenueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/venues")
@Tag(name = "Venue Controller", description = "API for managing venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    // Get all venues with pagination and sorting
    @GetMapping
    @Operation(summary = "Get All Venues", description = "Retrieve all venues with pagination and sorting support.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venues retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public Page<Venue> getAllVenues(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return venueService.getAllVenues(pageable);
    }

    // Get venue by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get Venue by ID", description = "Retrieve a specific venue by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue found"),
        @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Optional<Venue> venue = venueService.getVenueById(id);
        return venue.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new venue
    @PostMapping
    @Operation(summary = "Create Venue", description = "Create a new venue.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venue created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue createdVenue = venueService.createVenue(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
    }

    // Update venue details
    @PutMapping("/{id}")
    @Operation(summary = "Update Venue", description = "Update the details of an existing venue.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue updated successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    public ResponseEntity<Venue> updateVenue(
            @PathVariable Long id, 
            @RequestBody Venue venueDetails) {

        try {
            Venue updatedVenue = venueService.updateVenue(id, venueDetails);
            return ResponseEntity.ok(updatedVenue);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Find venues by name
    @GetMapping("/search")
    @Operation(summary = "Find Venues by Name", description = "Retrieve venues by their name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venues found"),
        @ApiResponse(responseCode = "404", description = "No venues found with the provided name")
    })
    public ResponseEntity<List<Venue>> findByName(@RequestParam String name) {
        List<Venue> venues = venueService.findByName(name);
        return ResponseEntity.ok(venues);
    }

    // Delete a venue
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Venue", description = "Delete a venue by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venue deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Venue not found")
    })
    public ResponseEntity<String> deleteVenue(@PathVariable Long id) {
        try {
            venueService.deleteVenue(id);
            return ResponseEntity.ok("Venue deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
