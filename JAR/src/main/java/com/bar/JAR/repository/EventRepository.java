package com.bar.JAR.repository;

import com.bar.JAR.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// This interface extends JpaRepository to provide basic CRUD operations and custom queries.
public interface EventRepository extends JpaRepository<Event, Long> {

    // Custom query to find events by venue ID
    List<Event> findByVenueID(long venueID);

    // Custom query to find events by contact ID
    List<Event> findByContactID(long contactID);

    // Custom query to find events by event restriction
    List<Event> findByEventRestriction(String eventRestriction);
}