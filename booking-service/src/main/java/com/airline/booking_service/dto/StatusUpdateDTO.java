package com.airline.booking_service.dto;

public class StatusUpdateDTO {
    private long id;
    private String status;

    public StatusUpdateDTO() {

    }

    public StatusUpdateDTO(long id, String status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
