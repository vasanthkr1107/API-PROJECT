package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Event;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.TicketRepository;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket createTicket(Long eventId, Ticket ticket) {
        // Set the event ID for the ticket
        ticket.setEventId(eventId);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getTicketsByEventId(Long eventId) {
        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getEventId().equals(eventId))
                .collect(Collectors.toList());
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }



    @Override
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        event.setName(eventDetails.getName());
        event.setDateTime(eventDetails.getDateTime()); // Updated to use getDateTime()

        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id) {
        // Optionally, you may want to delete tickets associated with the event
        ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getEventId().equals(id))
                .forEach(ticket -> ticketRepository.delete(ticket));

        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findByName(String name) {
        return eventRepository.findByName(name);
    }
}
