package com.bar.JAR.controller;

import com.bar.JAR.model.Contact;
import com.bar.JAR.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // GET all contacts
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            List<Contact> contacts = contactRepository.findAll();

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET contact by ID
    @GetMapping("/contacts/{contactID}")
    public ResponseEntity<Contact> getContactById(@PathVariable("contactID") long contactID) {
        Optional<Contact> contactData = contactRepository.findById(contactID);

        if (contactData.isPresent()) {
            return new ResponseEntity<>(contactData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST create a new contact
    @SuppressWarnings("null")
    @PostMapping("/contacts")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        try {
            Contact _contact = contactRepository.save(new Contact(
                    contact.getContactID(),
                    contact.getContactName(),
                    contact.getContactEmail(),
                    contact.getPhoneNumber()
            ));
            return new ResponseEntity<>(_contact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT update an existing contact
    @PutMapping("/contacts/{contactID}")
    public ResponseEntity<Contact> updateContact(@PathVariable("contactID") long contactID, @RequestBody Contact contact) {
        Optional<Contact> contactData = contactRepository.findById(contactID);

        if (contactData.isPresent()) {
            Contact _contact = contactData.get();
            _contact.setContactName(contact.getContactName());
            _contact.setContactEmail(contact.getContactEmail());
            _contact.setPhoneNumber(contact.getPhoneNumber());
            return new ResponseEntity<>(contactRepository.save(_contact), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE contact by ID
    @DeleteMapping("/contacts/{contactID}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("contactID") long contactID) {
        try {
            contactRepository.deleteById(contactID);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE all contacts
    @DeleteMapping("/contacts")
    public ResponseEntity<HttpStatus> deleteAllContacts() {
        try {
            contactRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET contact by email address
    @GetMapping("/contacts/email/{emailAddress}")
    public ResponseEntity<Contact> getContactByEmail(@PathVariable("emailAddress") String emailAddress) {
        try {
            Contact contact = contactRepository.findByEmailAddress(emailAddress);

            if (contact != null) {
                return new ResponseEntity<>(contact, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
