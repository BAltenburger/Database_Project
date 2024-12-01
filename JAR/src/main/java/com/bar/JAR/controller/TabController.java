
package com.bar.JAR.controller;

import com.bar.JAR.model.Tab;
import com.bar.JAR.repository.TabRepository;

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
public class TabController {

    @Autowired
    TabRepository tabRepository;

    // Get all tabs or filter by isOpen status
    @SuppressWarnings("null")
    @GetMapping("/tabs")
    public ResponseEntity<List<Tab>> getAllTabs(@RequestParam(required = false) Boolean isOpen) {
        try {
            List<Tab> tabs = new ArrayList<>();

            if (isOpen == null) {
                tabRepository.findAll().forEach(tabs::add);
            } else {
                tabRepository.findByIsOpen(isOpen).forEach(tabs::add); // Custom query to filter by open status
            }

            if (tabs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tabs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get tab by ID
    @GetMapping("/tabs/{id}")
    public ResponseEntity<Tab> getTabById(@PathVariable("id") long id) {
        Optional<Tab> tabData = tabRepository.findById(id);

        if (tabData.isPresent()) {
            return new ResponseEntity<>(tabData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new Tab
    @SuppressWarnings("null")
    @PostMapping("/tabs")
    public ResponseEntity<Tab> createTab(@RequestBody Tab tab) {
        try {
            // Create a new Tab and save it
            Tab _tab = tabRepository.save(new Tab(
                    tab.isOpen(),
                    tab.getTabAmount(),
                    tab.getMoneySpent(),
                    tab.getSignatureDrink()
            ));
            return new ResponseEntity<>(_tab, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing Tab
    @PutMapping("/tabs/{id}")
    public ResponseEntity<Tab> updateTab(@PathVariable("id") long id, @RequestBody Tab tab) {
        Optional<Tab> tabData = tabRepository.findById(id);

        if (tabData.isPresent()) {
            Tab _tab = tabData.get();
            _tab.setTabID(tab.getTabID()); // Update tabID
            _tab.setOpen(tab.isOpen()); // Update isOpen status
            _tab.setTabAmount(tab.getTabAmount());
            _tab.setMoneySpent(tab.getMoneySpent());
            _tab.setSignatureDrink(tab.getSignatureDrink());
            return new ResponseEntity<>(tabRepository.save(_tab), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a tab by ID
    @DeleteMapping("/tabs/{id}")
    public ResponseEntity<HttpStatus> deleteTab(@PathVariable("id") long id) {
        try {
            tabRepository.deleteById(id); // Corrected method call to delete by ID
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all tabs
    @DeleteMapping("/tabs")
    public ResponseEntity<HttpStatus> deleteAllTabs() {
        try {
            tabRepository.deleteAll(); // Correctly delete all records
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
