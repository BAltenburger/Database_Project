package com.bar.JAR.controller;


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
public class VenueController {

    @Autowired

    @GetMapping("/patients")
    public ResponseEntity<List<Venue>> getAllPatients(@RequestParam(required = false) String firstName) {
        try {
            List<Patient> patients = new ArrayList<Patient>();

            if (firstName == null)
                patientRepository.findAll().forEach(patients::add);
            else
                patientRepository.findByFirstNameContaining(firstName).forEach(patients::add);

            if (patients.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(patients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") long id) {
        Optional<Patient> patientData = patientRepository.findById(id);

        if (patientData.isPresent()) {
            return new ResponseEntity<>(patientData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        try {
            Patient _patient = patientRepository
                    .save(new Patient(patient.getFirstName(), patient.getLastName(), patient.getDob(), patient.getStreet()
                            ,patient.getCity(),patient.getState(),patient.getZipCode(),patient.getPhoneNo(),patient.getEmailAddress()));
            return new ResponseEntity<>(_patient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") long id, @RequestBody Patient patient) {
        Optional<Patient> patientData = patientRepository.findById(id);

        if (patientData.isPresent()) {
            Patient _patient= patientData.get();
            _patient.setFirstName(patient.getFirstName());
            _patient.setLastName(patient.getLastName());
            _patient.setDob(patient.getDob());
            _patient.setStreet(patient.getStreet());
            _patient.setCity(patient.getCity());
            _patient.setState(patient.getState());
            _patient.setZipCode(patient.getZipCode());
            _patient.setPhoneNo(patient.getPhoneNo());
            return new ResponseEntity<>(patientRepository.save(_patient), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<HttpStatus> deletePatient(@PathVariable("id") long id) {
        try {
            patientRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/patients")
    public ResponseEntity<HttpStatus> deleteAllPatients() {
        try {
            patientRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
