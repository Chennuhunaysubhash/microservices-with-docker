package com.airline.booking_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator",initialValue = 12000,allocationSize = 1,sequenceName = "booking_seq")
    private long id;

    private int cid;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String customerName;
    private long flightId;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String flightName;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String from;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String to;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String departure;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String time;
    private int noSeats;

    private long price;
    private long total;
    private String status;

    public Booking() {
    }

    public Booking(int cid, String customerName, long flightId, String flightName, String from, String to, String departure, String time, int noSeats, long price, long total, String status) {
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

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", flightName='" + flightName + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departure='" + departure + '\'' +
                ", time='" + time + '\'' +
                ", noSeats=" + noSeats +
                ", price=" + price +
                ", total=" + total +
                ", status='" + status + '\'' +
                '}';
    }
}
