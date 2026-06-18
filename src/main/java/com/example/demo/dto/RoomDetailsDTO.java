package com.example.demo.dto;

import com.example.demo.model.enums.RoomStatus;

public class RoomDetailsDTO {
    private Long id;
    private String number;
    private RoomStatus status;
    
    private Integer bedCount;
    
    private CurrentGuestDTO currentGuest;

    public RoomDetailsDTO() {}

    public RoomDetailsDTO(Long id, String number, RoomStatus status, Integer bedCount) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.bedCount = bedCount;
        this.currentGuest = null;
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

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public CurrentGuestDTO getCurrentGuest() {
        return currentGuest;
    }

    public void setCurrentGuest(CurrentGuestDTO currentGuest) {
        this.currentGuest = currentGuest;
    }
}
