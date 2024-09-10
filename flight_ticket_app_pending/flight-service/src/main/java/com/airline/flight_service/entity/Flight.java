package com.airline.flight_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator",initialValue = 1000,allocationSize = 1,sequenceName = "flight_seq")
    private long id;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String flightName;

    @NotEmpty(message = "name should be present")
    @Size(min=2,message = "name should be more than three letters")
    private String from;

    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String to;


    private String departure;

    @NotEmpty(message = "name should be present")
    @Size(min=8,max = 9,message = "name should be more than three letters")
    private String time;
    @NotNull
    @Min(value = 99,message = "minimum price starts 1 please enter the more than 0 ")
    private int totalSeats;
    private int bookedSeats;
    private int availableSeats;
    @Min(value = 99,message = "minimum price starts 99 please enter the more than 99 ")
    private long price;

    private String status;

    public Flight() {
    }

    public Flight(String flightName, String from, String to, String departure, String time, int totalSeats, int bookedSeats, int availableSeats, long price) {
        this.flightName = flightName;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.time = time;
        this.totalSeats = totalSeats;
        this.bookedSeats = bookedSeats;
        this.availableSeats = availableSeats;
        this.price = price;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightName='" + flightName + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departure=" + departure +
                ", time='" + time + '\'' +
                ", totalSeats=" + totalSeats +
                ", bookedSeats=" + bookedSeats +
                ", availableSeats=" + availableSeats +
                ", price=" + price +
                '}';
    }
}
