package com.bar.JAR.controller;

import com.bar.JAR.model.Event;
import com.bar.JAR.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // GET all events or filter by venue ID or contact ID
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(required = false) Long venueID,
            @RequestParam(required = false) Long contactID,
            @RequestParam(required = false) String eventRestriction) {
        try {
            List<Event> events;
            if (venueID != null) {
                events = eventRepository.findByVenueID(venueID);
            } else if (contactID != null) {
                events = eventRepository.findByContactID(contactID);
            } else if (eventRestriction != null) {
                events = eventRepository.findByEventRestriction(eventRestriction);
            } else {
                events = eventRepository.findAll();
            }

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET an event by ID
    @GetMapping("/events/{eventID}")
    public ResponseEntity<Event> getEventById(@PathVariable("eventID") long eventID) {
        Optional<Event> eventData = eventRepository.findById(eventID);

        if (eventData.isPresent()) {
            return new ResponseEntity<>(eventData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST a new event
    @SuppressWarnings("null")
    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            Event _event = eventRepository.save(new Event(
                    event.getEventStart(),
                    event.getEventEnd(),
                    event.getEventRestriction(),
                    event.getContactID(),
                    event.getVenueID(),
                    event.getAttendeeCount()
            ));
            return new ResponseEntity<>(_event, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update an event by ID
    @PutMapping("/events/{eventID}")
    public ResponseEntity<Event> updateEvent(@PathVariable("eventID") long eventID, @RequestBody Event event) {
        Optional<Event> eventData = eventRepository.findById(eventID);

        if (eventData.isPresent()) {
            Event _event = eventData.get();
            _event.setEventStart(event.getEventStart());
            _event.setEventEnd(event.getEventEnd());
            _event.setEventRestriction(event.getEventRestriction());
            _event.setContactID(event.getContactID());
            _event.setVenueID(event.getVenueID());
            _event.setAttendeeCount(event.getAttendeeCount());
            return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE an event by ID
    @DeleteMapping("/events/{eventID}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("eventID") long eventID) {
        try {
            eventRepository.deleteById(eventID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
