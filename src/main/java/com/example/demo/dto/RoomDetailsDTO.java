package com.example.demo.dto;

import com.example.demo.model.enums.RoomStatus;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomDetailsDTO {
    private Long id;
    private String number;
    private RoomStatus status;
    
    private Integer bedCount;
    
    @JsonProperty("base_daily_rate")
    private BigDecimal baseDailyRate;
    
    private CurrentGuestDTO currentGuest;

    public RoomDetailsDTO() {}

    public RoomDetailsDTO(Long id, String number, RoomStatus status, Integer bedCount, BigDecimal baseDailyRate) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.bedCount = bedCount;
        this.baseDailyRate = baseDailyRate;
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

    public BigDecimal getBaseDailyRate() {
        return baseDailyRate;
    }

    public void setBaseDailyRate(BigDecimal baseDailyRate) {
        this.baseDailyRate = baseDailyRate;
    }
}
