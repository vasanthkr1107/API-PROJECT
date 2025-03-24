package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Organizer;
import com.example.demo.repository.OrganizerRepository;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @Override
    public Page<Organizer> getAllOrganizers(Pageable pageable) {
        return organizerRepository.findAll(pageable);
    }

    @Override
    public Optional<Organizer> getOrganizerById(Long id) {
        return organizerRepository.findById(id);
    }

    @Override
    public Organizer createOrganizer(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Override
    public Organizer updateOrganizer(Long id, Organizer organizerDetails) {
        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizer not found with id: " + id));

        organizer.setName(organizerDetails.getName());
        organizer.setEmail(organizerDetails.getEmail());
        organizer.setPhone(organizerDetails.getPhone());

        return organizerRepository.save(organizer);
    }

    @Override
    public void deleteOrganizer(Long id) {
        if (!organizerRepository.existsById(id)) {
            throw new RuntimeException("Organizer not found with id: " + id);
        }
        organizerRepository.deleteById(id);
    }

    @Override
    public List<Organizer> findByName(String name) {
        return organizerRepository.findByName(name);
    }
}
