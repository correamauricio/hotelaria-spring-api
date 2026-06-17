package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import com.example.demo.model.enums.RoomStatus;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_number")
    private String number;

    private String category;

    @Column(name = "bed_count")
    private Integer bedCount;

    @Column(name = "base_daily_rate")
    private BigDecimal baseDailyRate;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getBedCount() {
        return bedCount;
    }

    public void setBedCount(Integer bedCount) {
        this.bedCount = bedCount;
    }

    public BigDecimal getBaseDailyRate() {
        return baseDailyRate;
    }

    public void setBaseDailyRate(BigDecimal baseDailyRate) {
        this.baseDailyRate = baseDailyRate;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
