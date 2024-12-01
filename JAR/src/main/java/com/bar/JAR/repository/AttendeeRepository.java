package com.bar.JAR.repository;

import com.bar.JAR.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
    // JpaRepository provides basic CRUD methods like save(), findById(), deleteById(), etc.
}
