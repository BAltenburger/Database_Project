package com.bar.JAR.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Attendee")

public class Attendee {
    @Id
    @Column(name = "attendee_id")
    private int attendee_id;

    @Column(name = "attendee_name")
    private String attendee_name;

    @Column(name = "attendee_phone_number")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Phone number must be in the format xxx-xxx-xxxx")
    private String attendee_phone_number;

    public Attendee(){

    }

    public Attendee(int attendee_id, String attendee_name, String attendee_phone_number) {
        this.attendee_id = attendee_id;
        this.attendee_name = attendee_name;
        this.attendee_phone_number = attendee_phone_number;
    }

    public int getAttendeeId() {
        return attendee_id;
    }

    public void setAttendeeId(int attendee_id) {
        this.attendee_id = attendee_id;
    }

    public String getAttendeeName() {
        return attendee_name;
    }

    public void setAttendeeName(String attendee_name) {
        this.attendee_name = attendee_name;
    }

    public String getAttendeePhoneNumber() {
        return attendee_phone_number;
    }

    public void setAttendeePhoneNumber(String attendee_phone_number) {
        this.attendee_phone_number = attendee_phone_number;
    }
}
