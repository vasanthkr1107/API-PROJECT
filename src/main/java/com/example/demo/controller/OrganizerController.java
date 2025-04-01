package com.example.demo.controller;

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

import com.example.demo.entity.Organizer;
import com.example.demo.service.OrganizerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/organizers")
@Tag(name = "Organizer Controller", description = "API for managing event organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    // Get all organizers with pagination and sorting
    @GetMapping
    @Operation(summary = "Get All Organizers", description = "Retrieve all organizers with pagination and sorting support.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Organizers retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public Page<Organizer> getAllOrganizers(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return organizerService.getAllOrganizers(pageable);
    }

    // Get organizer by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get Organizer by ID", description = "Retrieve a specific organizer by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Organizer found"),
        @ApiResponse(responseCode = "404", description = "Organizer not found")
    })
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable Long id) {
        Optional<Organizer> organizer = organizerService.getOrganizerById(id);
        return organizer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new organizer
    @PostMapping
    @Operation(summary = "Create Organizer", description = "Create a new organizer.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Organizer created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Organizer> createOrganizer(@RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.createOrganizer(organizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganizer);
    }

    // Update organizer details
    @PutMapping("/{id}")
    @Operation(summary = "Update Organizer", description = "Update the details of an existing organizer.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Organizer updated successfully"),
        @ApiResponse(responseCode = "404", description = "Organizer not found")
    })
    public ResponseEntity<Organizer> updateOrganizer(
            @PathVariable Long id, 
            @RequestBody Organizer organizerDetails) {
        
        try {
            Organizer updatedOrganizer = organizerService.updateOrganizer(id, organizerDetails);
            return ResponseEntity.ok(updatedOrganizer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an organizer
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Organizer", description = "Delete an organizer by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Organizer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Organizer not found")
    })
    public ResponseEntity<String> deleteOrganizer(@PathVariable Long id) {
        try {
            organizerService.deleteOrganizer(id);
            return ResponseEntity.ok("Organizer deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
