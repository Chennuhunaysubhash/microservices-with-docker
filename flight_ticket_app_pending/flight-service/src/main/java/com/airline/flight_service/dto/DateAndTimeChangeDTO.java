package com.airline.flight_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class DateAndTimeChangeDTO {

    private long id;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String flightName;
    private String departure;
    @NotEmpty(message = "name should be present")
    @Size(min=8,max = 9,message = "name should be more than three letters")
    private String time;

    public DateAndTimeChangeDTO() {
    }

    public DateAndTimeChangeDTO(long id, String flightName, String departure, String time) {
        this.id = id;
        this.flightName = flightName;
        this.departure = departure;
        this.time = time;
    }

    public DateAndTimeChangeDTO(long id, String flightName, String time) {
        this.id = id;
        this.flightName = flightName;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
