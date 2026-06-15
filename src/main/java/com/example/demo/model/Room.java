package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long room_id;
    private String room_number;
    private String category;
    private BigDecimal base_daily_rate;
    private String status;

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getBase_daily_rate() {
        return base_daily_rate;
    }

    public void setBase_daily_rate(BigDecimal base_daily_rate) {
        this.base_daily_rate = base_daily_rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
