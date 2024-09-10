package com.airline.payment_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "payment_generator")
    @SequenceGenerator(name = "payment_generator",initialValue = 70001,allocationSize = 1,sequenceName = "payment_seq")
    private long id;
    @NotNull(message = "Cid should be present")
    private int cId;
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String customerName;
    @NotNull(message = "Cid should be present")
    private long bookingId;
    private long flightId;
    @NotEmpty(message = "flight name should be present")
    @Size(min=3,message = "flight name should be more than three letters")
    private String flightName;
    private int noSeats;
    private long price;
    private long total;
    private String paymentType;
    private String status;

    private Boolean invoiceStatus;


    public Payment() {
    }

    public Payment(int cId, String customerName, long bookingId, long flightId, String flightName, int noSeats, long price, long total, String paymentType, String status, Boolean invoiceStatus) {
        this.cId = cId;
        this.customerName = customerName;
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.flightName = flightName;
        this.noSeats = noSeats;
        this.price = price;
        this.total = total;
        this.paymentType = paymentType;
        this.status = status;
        this.invoiceStatus = invoiceStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public Boolean getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Boolean invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", cId=" + cId +
                ", customerName='" + customerName + '\'' +
                ", bookingId=" + bookingId +
                ", flightId=" + flightId +
                ", flightName='" + flightName + '\'' +
                ", noSeats=" + noSeats +
                ", price=" + price +
                ", total=" + total +
                ", paymentType='" + paymentType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
