package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;
import com.example.demo.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Controller", description = "API for managing events and associated tickets")
public class EventController {

    @Autowired
    private EventService eventService;

    // Get all events with pagination and sorting
    @GetMapping
    @Operation(summary = "Get All Events", description = "Retrieve all events with pagination and sorting support.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public Page<Event> getAllEvents(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventService.getAllEvents(pageable);
    }

    // Get event by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get Event by ID", description = "Retrieve a specific event by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event found"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new event
    @PostMapping
    @Operation(summary = "Create Event", description = "Create a new event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Event created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    // Update event details
    @PutMapping("/{id}")
    @Operation(summary = "Update Event", description = "Update the details of an existing event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event updated successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Find events by name
    @GetMapping("/search")
    @Operation(summary = "Find Events by Name", description = "Search for events by their name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Events found"),
        @ApiResponse(responseCode = "404", description = "No events found with the specified name")
    })
    public ResponseEntity<List<Event>> findByName(@RequestParam String name) {
        List<Event> events = eventService.findByName(name);
        return ResponseEntity.ok(events);
    }

    // Delete an event
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Event", description = "Delete an event by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a ticket for an event
     
    @Operation(summary = "Create Ticket", description = "Create a new ticket for a specific event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
        @ApiResponse(responseCode = "404", description = "Event not found")
    })
    public ResponseEntity<Ticket> createTicket(@PathVariable Long eventId, @RequestBody Ticket ticket) {
        Ticket createdTicket = eventService.createTicket(eventId, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // Get tickets by event ID
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
