package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomSummaryDTO {
    private Long id;
    
    @JsonProperty("room_number")
    private String number;
    
    private String status;

    public RoomSummaryDTO() {}

    public RoomSummaryDTO(Long id, String number, String status) {
        this.id = id;
        this.number = number;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
