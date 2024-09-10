package com.airline.flight_service.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookingRestartDTO {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator",initialValue = 1000,allocationSize = 1,sequenceName = "flight_seq")
    private long id;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String flightName;
    @NotNull
    @Min(value = 99,message = "minimum price starts 1 please enter the more than 0 ")
    private int totalSeats;
    private int bookedSeats;
    private int availableSeats;
    private String status;

    public BookingRestartDTO() {
    }

    public BookingRestartDTO(long id, String flightName, int totalSeats, int availableSeats) {
        this.id = id;
        this.flightName = flightName;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
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

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
