package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Additional query methods can be defined here if needed
}
