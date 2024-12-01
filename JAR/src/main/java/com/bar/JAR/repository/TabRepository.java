package com.bar.JAR.repository;

import com.bar.JAR.model.Tab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabRepository extends JpaRepository<Tab, Long> {

    // Custom query method to find tabs based on the 'isOpen' status
    List<Tab> findByIsOpen(boolean isOpen);

}