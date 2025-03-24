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

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    // Get all organizers with pagination and sorting
    @GetMapping
    public Page<Organizer> getAllOrganizers(@RequestParam(defaultValue = "0") int page, 
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return organizerService.getAllOrganizers(pageable);
    }

    // Get organizer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable Long id) {
        Optional<Organizer> organizer = organizerService.getOrganizerById(id);
        return organizer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new organizer
    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(@RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.createOrganizer(organizer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganizer);
    }

    // Update organizer details
    @PutMapping("/{id}")
    public ResponseEntity<Organizer> updateOrganizer(@PathVariable Long id, @RequestBody Organizer organizerDetails) {
        try {
            Organizer updatedOrganizer = organizerService.updateOrganizer(id, organizerDetails);
            return ResponseEntity.ok(updatedOrganizer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an organizer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizer(@PathVariable Long id) {
        try {
            organizerService.deleteOrganizer(id);
            return ResponseEntity.ok("Organizer deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
