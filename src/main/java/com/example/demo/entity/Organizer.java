package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizers")
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToOne



    @JoinColumn(name = "venue_id")
    @JsonBackReference  // Prevent infinite recursion
    private Venue venue;

    // Constructors
    public Organizer() {}

    public Organizer(String name, String email, String phone, Venue venue) {


        this.name = name;
        this.email = email;
        this.phone = phone;
        this.venue = venue;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }


    public Venue getVenue() { return venue; }

    public void setVenue(Venue venue) { this.venue = venue; }
}
