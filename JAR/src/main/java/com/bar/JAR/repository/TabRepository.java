package main.java.com.bar.JAR.repository;

import main.java.com.bar.JAR.model.Tab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabRepository extends JpaRepository<Tab,Long> {
    List<Tab> findByTabID(String TabID);
}
