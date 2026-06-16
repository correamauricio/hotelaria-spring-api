package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoomDTO {
    private Long id;
    private String number;
    private String status;
    
    @JsonProperty("bed_count")
    private Integer bedCount;

    public RoomDTO() {
    }

    public RoomDTO(Long id, String number, String status, Integer bedCount) {
        this.id = id;
        this.number = number;
        this.status = status;
        this.bedCount = bedCount;
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

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }
}
