/** Model for Event **/
package main.java.com.bar.JAR.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private long eventID;

    @Column(name = "event_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventStart;

    @Column(name = "event_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventEnd;

    @Column(name = "event_restriction")
    private String eventRestriction;

    @Column(name = "contact_id")
    private long contactID;

    @Column(name = "venue_id")
    private long venueID;

    @Column(name = "attendee_count")
    private int attendeeCount;

    public Event() {
    }

    public Event(Date eventStart, Date eventEnd, String eventRestriction, long contactID, long venueID, int attendeeCount) {
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventRestriction = eventRestriction;
        this.contactID = contactID;
        this.venueID = venueID;
        this.attendeeCount = attendeeCount;
    }

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Date getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Date eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getEventRestriction() {
        return eventRestriction;
    }

    public void setEventRestriction(String eventRestriction) {
        this.eventRestriction = eventRestriction;
    }

    public long getContactID() {
        return contactID;
    }

    public void setContactID(long contactID) {
        this.contactID = contactID;
    }

    public long getVenueID() {
        return venueID;
    }

    public void setVenueID(long venueID) {
        this.venueID = venueID;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }
}
