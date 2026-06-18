package com.example.demo.model;

import jakarta.persistence.*;
import com.example.demo.model.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "expected_check_in_date", nullable = false)
    private LocalDate expectedCheckInDate;

    @Column(name = "expected_check_out_date", nullable = false)
    private LocalDate expectedCheckOutDate;

    @Column(name = "applied_daily_rate", nullable = false)
    private BigDecimal appliedDailyRate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public Reservation() {
    }

    public Reservation(Guest guest, Room room, LocalDate expectedCheckInDate, LocalDate expectedCheckOutDate, BigDecimal appliedDailyRate, BigDecimal totalAmount, String notes) {
        this.guest = guest;
        this.room = room;
        this.expectedCheckInDate = expectedCheckInDate;
        this.expectedCheckOutDate = expectedCheckOutDate;
        this.appliedDailyRate = appliedDailyRate;
        this.totalAmount = totalAmount;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
