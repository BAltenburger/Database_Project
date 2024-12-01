package com.bar.JAR.repository;

import com.bar.JAR.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
    // JpaRepository provides CRUD operations out of the box
}
