package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ticket;
import com.example.demo.service.EventService;

@RestController
@RequestMapping("/api/tickets")

public class TicketController {

    @Autowired
    private EventService eventService;

    // Create a new ticket for an event
    @PostMapping("/{eventId}/tickets")
    public ResponseEntity<Ticket> createTicket(@PathVariable Long eventId, @RequestBody Ticket ticket) {
        Ticket createdTicket = eventService.createTicket(eventId, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // Get tickets for a specific event
    @GetMapping("/{eventId}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable Long eventId) {
        List<Ticket> tickets = eventService.getTicketsByEventId(eventId);
        return ResponseEntity.ok(tickets);
    }
}