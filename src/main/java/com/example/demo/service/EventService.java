package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;

public interface EventService {

    Ticket createTicket(Long eventId, Ticket ticket);
    List<Ticket> getTicketsByEventId(Long eventId);


    Page<Event> getAllEvents(Pageable pageable);

    Optional<Event> getEventById(Long id);

    Event createEvent(Event event);

    Event updateEvent(Long id, Event eventDetails);

    void deleteEvent(Long id);

    List<Event> findByName(String name);
}
