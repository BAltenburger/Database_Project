package com.bar.JAR.controller;

import com.bar.JAR.model.Venue;
import com.bar.JAR.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueRepository venueRepository;

    // Get all venues
    @GetMapping
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // Get venue by ID
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable("id") int id) {
        Optional<Venue> venue = venueRepository.findById(id);
        return venue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new venue (POST)
    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue savedVenue = venueRepository.save(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
    }

    // Update an existing venue (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable("id") int id, @RequestBody Venue venueDetails) {
        Optional<Venue> existingVenue = venueRepository.findById(id);

        if (existingVenue.isPresent()) {
            Venue venueToUpdate = existingVenue.get();
            venueToUpdate.setVenueName(venueDetails.getVenueName());
            venueToUpdate.setVenueWebsite(venueDetails.getVenueWebsite());
            venueToUpdate.setTotalCapacity(venueDetails.getTotalCapacity());
            venueToUpdate.setStreetAddress(venueDetails.getStreetAddress());
            venueToUpdate.setCity(venueDetails.getCity());
            venueToUpdate.setState(venueDetails.getState());
            venueToUpdate.setZip(venueDetails.getZip());

            Venue updatedVenue = venueRepository.save(venueToUpdate);
            return ResponseEntity.ok(updatedVenue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete venue by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable("id") int id) {
        Optional<Venue> venue = venueRepository.findById(id);

        if (venue.isPresent()) {
            venueRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
