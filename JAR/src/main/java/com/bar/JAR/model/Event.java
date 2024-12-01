/** Model for Event **/
package com.bar.JAR.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @Column(name = "event_id")
    private int eventID;

    //researched JSON formatting, etc. for dates to make sure everything works properly
    @Column(name = "event_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "EST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventStart;

    @Column(name = "event_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "EST")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventEnd;

    @Column(name = "event_restriction")
    private String eventRestriction;

    @Column(name = "attendee_count")
    private int attendeeCount;

    public Event() {
    }

    public Event(int eventID, Date eventStart, Date eventEnd, String eventRestriction, int attendeeCount) {
        this.eventID = eventID;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventRestriction = eventRestriction;
        this.attendeeCount = attendeeCount;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
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

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }
}
