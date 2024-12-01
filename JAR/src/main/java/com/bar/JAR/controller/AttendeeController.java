package com.bar.JAR.controller;

import com.bar.JAR.model.Attendee;
import com.bar.JAR.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AttendeeController {

    @Autowired
    private AttendeeRepository attendeeRepository;

    // GET all attendees
    @GetMapping("/attendees")
    public ResponseEntity<List<Attendee>> getAllAttendees() {
        try {
            List<Attendee> attendees = attendeeRepository.findAll();

            if (attendees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(attendees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET attendee by ID
    @GetMapping("/attendees/{attendeeId}")
    public ResponseEntity<Attendee> getAttendeeById(@PathVariable("attendeeId") int attendeeId) {
        Optional<Attendee> attendeeData = attendeeRepository.findById(attendeeId);

        if (attendeeData.isPresent()) {
            return new ResponseEntity<>(attendeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST create a new attendee
    @SuppressWarnings("null")
    @PostMapping("/attendees")
    public ResponseEntity<Attendee> createAttendee(@RequestBody Attendee attendee) {
        // Validate phone number format manually
        if (!isValidPhoneNumber(attendee.getAttendeePhoneNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid phone number format
        }

        try {
            Attendee savedAttendee = attendeeRepository.save(new Attendee(
                    attendee.getAttendeeId(),
                    attendee.getAttendeeName(),
                    attendee.getAttendeePhoneNumber()
            ));
            return new ResponseEntity<>(savedAttendee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update an existing attendee
    @PutMapping("/attendees/{attendeeId}")
    public ResponseEntity<Attendee> updateAttendee(@PathVariable("attendeeId") int attendeeId, @RequestBody Attendee attendee) {
        // Validate phone number format manually
        if (!isValidPhoneNumber(attendee.getAttendeePhoneNumber())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid phone number format
        }

        Optional<Attendee> attendeeData = attendeeRepository.findById(attendeeId);

        if (attendeeData.isPresent()) {
            Attendee updatedAttendee = attendeeData.get();
            updatedAttendee.setAttendeeName(attendee.getAttendeeName());
            updatedAttendee.setAttendeePhoneNumber(attendee.getAttendeePhoneNumber());
            return new ResponseEntity<>(attendeeRepository.save(updatedAttendee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE attendee by ID
    @DeleteMapping("/attendees/{attendeeId}")
    public ResponseEntity<HttpStatus> deleteAttendee(@PathVariable("attendeeId") int attendeeId) {
        try {
            attendeeRepository.deleteById(attendeeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Helper method to validate phone number format (xxx-xxx-xxxx)
    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "\\d{3}-\\d{3}-\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
