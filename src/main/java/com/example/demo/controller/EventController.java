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

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Get all events with pagination and sorting
    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page, 
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventService.getAllEvents(pageable);
    }

    // Get event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    // Update event details
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // New method to find events by name
    @GetMapping("/search")
    public ResponseEntity<List<Event>> findByName(@RequestParam String name) {
        List<Event> events = eventService.findByName(name);
        return ResponseEntity.ok(events);
    }

    // Delete an event
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // New method to create a ticket for an event
    @PostMapping("/{eventId}/tickets")
    public ResponseEntity<Ticket> createTicket(@PathVariable Long eventId, @RequestBody Ticket ticket) {
        Ticket createdTicket = eventService.createTicket(eventId, ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // New method to get tickets for an event
    @GetMapping("/{eventId}/tickets")
    public ResponseEntity<List<Ticket>> getTicketsByEventId(@PathVariable Long eventId) {
        List<Ticket> tickets = eventService.getTicketsByEventId(eventId);
        return ResponseEntity.ok(tickets);
    }
}
