package com.airline.booking_service.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BookingDTO {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator",initialValue = 12000,allocationSize = 1,sequenceName = "booking_seq")
    private long id;
    private int cid;
    private String customerName;
    private long flightId;

    private String flightName;

    private String from;

    private String to;

    private String departure;
    private String time;
    private int noSeats;

    private long price;
    private long total;
    private String status;

    public BookingDTO() {
    }

    public BookingDTO(int cid, String customerName, long flightId, String flightName, String from, String to, String departure, String time, int noSeats, long price, long total, String status) {
        this.cid = cid;
        this.customerName = customerName;
        this.flightId = flightId;
        this.flightName = flightName;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.time = time;
        this.noSeats = noSeats;
        this.price = price;
        this.total = total;
        this.status = status;
    }

    public BookingDTO(int cid, long flightId, String flightName, String from, String to, String departure, String time, int noSeats, int price, int total, String status) {
        this.cid = cid;
        this.flightId = flightId;
        this.flightName = flightName;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.time = time;
        this.noSeats = noSeats;
        this.price = price;
        this.total = total;
        this.status = status;
    }

    public BookingDTO(int cid, long flightId, int noSeats, int price, int total, String status) {
        this.cid = cid;
        this.flightId = flightId;
        this.noSeats = noSeats;
        this.price = price;
        this.total = total;
        this.status = status;
    }

    public BookingDTO(int cid, long flightId, int noSeats) {
        this.cid = cid;
        this.flightId = flightId;
        this.noSeats = noSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
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

    public int getNoSeats() {
        return noSeats;
    }

    public void setNoSeats(int noSeats) {
        this.noSeats = noSeats;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
