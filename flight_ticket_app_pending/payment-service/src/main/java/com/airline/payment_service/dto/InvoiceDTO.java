package com.airline.payment_service.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class InvoiceDTO {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "invoice_generator")
    @SequenceGenerator(name = "invoice_generator",initialValue = 100,allocationSize = 1,sequenceName = "invoice_seq")
    private long id;
    private int cid;
    private long paymentId;
    @NotEmpty(message = "Should be present")
    private String  definition;
    @CreationTimestamp
    private LocalDateTime localDateTime;

    public InvoiceDTO() {
    }

    public InvoiceDTO(long id, int cid, long paymentId, String definition, LocalDateTime localDateTime) {
        this.id = id;
        this.cid = cid;
        this.paymentId = paymentId;
        this.definition = definition;
        this.localDateTime = localDateTime;
    }

    public InvoiceDTO(int cid, long paymentId, String definition, LocalDateTime localDateTime) {
        this.cid = cid;
        this.paymentId = paymentId;
        this.definition = definition;
        this.localDateTime = localDateTime;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
