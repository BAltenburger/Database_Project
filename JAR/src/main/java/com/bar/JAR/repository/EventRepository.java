package main.java.com.bar.JAR.repository;

import main.java.com.bar.JAR.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByEventID(String EventID);
}
