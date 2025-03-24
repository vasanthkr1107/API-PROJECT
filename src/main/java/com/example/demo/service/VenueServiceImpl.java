package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Venue;
import com.example.demo.repository.VenueRepository;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Override
    public Page<Venue> getAllVenues(Pageable pageable) {
        return venueRepository.findAll(pageable);
    }

    @Override
    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public Venue updateVenue(Long id, Venue venueDetails) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found with id: " + id));

        venue.setName(venueDetails.getName());
        venue.setLocation(venueDetails.getLocation()); // Assuming there's a setLocation method

        return venueRepository.save(venue);
    }

    @Override
    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new RuntimeException("Venue not found with id: " + id);
        }
        venueRepository.deleteById(id);
    }

    @Override
    public List<Venue> findByName(String name) {
        return venueRepository.findByName(name);
    }
}
