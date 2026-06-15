package com.example.demo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "guest_id", nullable = false)
    private int guestId;

    @Column(name = "room_id",nullable = false)
    private int roomId;

    @Column(name = "expected_check_in_date", nullable = false)
    private LocalDate expectedCheckInDate;

    @Column(name = "expected_check_out_date", nullable = false)
    private LocalDate expectedCheckOutDate;

    @Column(name = "applied_daily_rate", nullable = false)
    private BigDecimal appliedDailyRate;

    @Column(name = "status")
    private String status;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public Reservation() {
    }

    public Reservation(int guestId, int roomId, LocalDate expectedCheckInDate, LocalDate expectedCheckOutDate, BigDecimal appliedDailyRate, String notes) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.expectedCheckInDate = expectedCheckInDate;
        this.expectedCheckOutDate = expectedCheckOutDate;
        this.appliedDailyRate = appliedDailyRate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getExpectedCheckInDate() {
        return expectedCheckInDate;
    }

    public void setExpectedCheckInDate(LocalDate expectedCheckInDate) {
        this.expectedCheckInDate = expectedCheckInDate;
    }

    public LocalDate getExpectedCheckOutDate() {
        return expectedCheckOutDate;
    }

    public void setExpectedCheckOutDate(LocalDate expectedCheckOutDate) {
        this.expectedCheckOutDate = expectedCheckOutDate;
    }

    public BigDecimal getAppliedDailyRate() {
        return appliedDailyRate;
    }

    public void setAppliedDailyRate(BigDecimal appliedDailyRate) {
        this.appliedDailyRate = appliedDailyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
