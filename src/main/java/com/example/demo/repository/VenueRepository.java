package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    
    @Query("SELECT v FROM Venue v WHERE v.name = :name")
    List<Venue> findByName(@Param("name") String name);

}
