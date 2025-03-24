package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Venue;

public interface VenueService {

    Page<Venue> getAllVenues(Pageable pageable);

    Optional<Venue> getVenueById(Long id);

    Venue createVenue(Venue venue);

    Venue updateVenue(Long id, Venue venueDetails);

    void deleteVenue(Long id);

    List<Venue> findByName(String name);
}
