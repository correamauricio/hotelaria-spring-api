package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.demo.model.enums.RoomStatus;

public class RoomSummaryDTO {
    private Long id;
    
    @JsonProperty("room_number")
    private String number;
    
    private RoomStatus status;

    public RoomSummaryDTO() {}

    public RoomSummaryDTO(Long id, String number, RoomStatus status) {
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

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
