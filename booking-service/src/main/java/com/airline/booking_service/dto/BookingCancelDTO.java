package com.airline.booking_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookingCancelDTO {
    @NotNull(message = "Should be present")
    private long id;
    @NotNull(message = "Should be present")
    private int cid;
    @NotNull(message = "Should be present")
    private long total;
    @NotEmpty(message = "Should be present")
    private String cancelingDate;

    public BookingCancelDTO() {
    }

    public BookingCancelDTO(long id, int cid, long total, String cancelingDate) {
        this.id = id;
        this.cid = cid;
        this.total = total;
        this.cancelingDate = cancelingDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getCancelingDate() {
        return cancelingDate;
    }

    public void setCancelingDate(String cancelingDate) {
        this.cancelingDate = cancelingDate;
    }
}
