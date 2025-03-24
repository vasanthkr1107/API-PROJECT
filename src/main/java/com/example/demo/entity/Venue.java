package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int capacity;

    @OneToOne(mappedBy = "venue")



    @JsonManagedReference  // Handle circular reference
    private Organizer organizer;

    // Constructors
    public Venue() {}

    public Venue(String name, String location, int capacity, Organizer organizer) {


        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.organizer = organizer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }


    public Organizer getOrganizer() { return organizer; }

    public void setOrganizer(Organizer organizer) { this.organizer = organizer; }
}
