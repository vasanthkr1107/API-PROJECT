package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Event;
import com.example.demo.entity.Organizer;
import com.example.demo.entity.Venue;

public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByVenue(Venue venue);
    List<Event> findByOrganizer(Organizer organizer);

    @Query("SELECT e FROM Event e WHERE e.name = :name")
    List<Event> findByName(@Param("name") String name);
}
