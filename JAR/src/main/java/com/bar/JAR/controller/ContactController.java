package main.java.com.bar.JAR.controller;


import main.java.com.bar.JAR.model.Event;
import main.java.com.bar.JAR.repository.EventRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ContactController {
  @Autowired
  ContactRepository contactRepository;

  @GetMapping("/contacts")
  public ResponseEntity<List<Contact>> getAllContacts(@RequestParam(required = false) String contactID) {
        try {
            List<Contact> contacts = new ArrayList<Contact>();

            if (contactID == null)
                contactRepository.findAll().forEach(contacts::add);
            else
                contactRepository.findByContactID(contactID).forEach(contacts::add);

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @GetMapping("/contacts/{id}")
  // TODO

  @PostMapping("/contacts")
  public ResponseEntity<Contact> createContact(@RequestBody Contact c) {
        try {
            Contact _c = contactRepository.save(new Contact(c.getContactID(), c.getContactName(), c.getContactEmail(), c.getContactPhoneNumber()));
            return new ResponseEntity<>(_c, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @PutMapping("/contacts/{id}")
   // TODO

  @DeleteMapping("/contacts/{id}")
  public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") long id) {
        try {
            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
  
  
}
