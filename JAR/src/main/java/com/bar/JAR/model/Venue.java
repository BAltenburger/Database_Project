package com.bar.JAR.model;

import jakarta.persistence.*;


@Entity
@Table(name = "Venue")
public class Venue {
    // CREATE TABLE Venue(
    //     venue_id INT PRIMARY KEY,
    //     venue_name VARCHAR(50),
    //     venue_website VARCHAR(50),
    //     total_capacity INT,
    //     street_address VARCHAR(100),
    //     city VARCHAR(30),
    //     [state] VARCHAR(5),
    //     zip INT
    // );
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private int venue_id;

    @Column(name = "venue_name")
    private String venue_name;

    @Column(name = "venue_website")
    private String venue_website;

    @Column(name = "total_capacity")
    private int total_capacity;

    @Column(name = "street_address")
    private String street_address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private int zip;

    public Venue(){

    }

    public Venue(int venue_id, String venue_name, String venue_website, int total_capacity, String street_address, String city, String state, int zip) {
        this.venue_id = venue_id;
        this.venue_name = venue_name;
        this.venue_website = venue_website;
        this.total_capacity = total_capacity;
        this.street_address = street_address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getVenueId() {
        return venue_id;
    }

    public void setVenueId(int venue_id) {
        this.venue_id = venue_id;
    }

    public String getVenueName() {
        return venue_name;
    }

    public void setVenueName(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getVenueWebsite() {
        return venue_website;
    }

    public void setVenueWebsite(String venue_website) {
        this.venue_website = venue_website;
    }

    public int getTotalCapacity() {
        return total_capacity;
    }

    public void setTotalCapacity(int total_capacity) {
        this.total_capacity = total_capacity;
    }

    public String getStreetAddress() {
        return street_address;
    }

    public void setStreetAddress(String street_address) {
        this.street_address = street_address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getZip() {
        return zip;
    }

    public void setZipCode(int zip) {
        this.zip = zip;
    }



}
