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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Ticket Controller", description = "API for managing event tickets")
public class TicketController {

    @Autowired
    private EventService eventService;

    // Create a new ticket for an event
    @PostMapping("/{eventId}/tickets")
    @Operation(summary = "Create Ticket", description = "Create a new ticket for a specific event by event ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request body or event ID"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Ticket> createTicket(@PathVariable Long eventId, @RequestBody Ticket ticket) {
        Ticket createdTicket = eventService.createTicket(eventId, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // Get tickets for a specific event
    @GetMapping("/{eventId}/tickets")
    @Operation(summary = "Get Tickets by Event ID", description = "Retrieve all tickets for a given event ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No tickets found for the event")
    })
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable Long eventId) {
        List<Ticket> tickets = eventService.getTicketsByEventId(eventId);
        return ResponseEntity.ok(tickets);
    }
}
