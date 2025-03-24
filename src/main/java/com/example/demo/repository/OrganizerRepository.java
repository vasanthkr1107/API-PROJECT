package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    
    @Query("SELECT o FROM Organizer o WHERE o.name = :name")
    List<Organizer> findByName(@Param("name") String name);

}
