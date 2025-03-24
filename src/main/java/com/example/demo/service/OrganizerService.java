package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Organizer;

public interface OrganizerService {

    Page<Organizer> getAllOrganizers(Pageable pageable);

    Optional<Organizer> getOrganizerById(Long id);

    Organizer createOrganizer(Organizer organizer);

    Organizer updateOrganizer(Long id, Organizer organizerDetails);

    void deleteOrganizer(Long id);

    List<Organizer> findByName(String name);
}
