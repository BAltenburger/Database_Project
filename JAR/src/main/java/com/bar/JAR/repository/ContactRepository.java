package com.bar.JAR.repository;

import com.bar.JAR.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

// This interface extends JpaRepository to provide basic CRUD operations for Contact.
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // You can add custom queries if needed, e.g., find contact by email
    Contact findByEmailAddress(String emailAddress);
}
