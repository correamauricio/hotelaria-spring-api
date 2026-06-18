package com.example.demo.dto;

import com.example.demo.model.Reservation;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationResponseDTO {

    private Long id;
    private Long guestId;
    private Long roomId;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private BigDecimal totalAmount;
    private String status;

    public ReservationResponseDTO() {
    }

    public ReservationResponseDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.guestId = reservation.getGuest() != null ? reservation.getGuest().getGuestId() : null;
        this.roomId = reservation.getRoom() != null ? reservation.getRoom().getId() : null;
        this.checkinDate = reservation.getExpectedCheckInDate();
        this.checkoutDate = reservation.getExpectedCheckOutDate();
        this.totalAmount = reservation.getTotalAmount();
        if (this.totalAmount == null && this.checkinDate != null && this.checkoutDate != null && reservation.getAppliedDailyRate() != null) {
            long days = java.time.temporal.ChronoUnit.DAYS.between(this.checkinDate, this.checkoutDate);
            this.totalAmount = reservation.getAppliedDailyRate().multiply(BigDecimal.valueOf(days));
        }
        this.status = reservation.getStatus() != null ? reservation.getStatus().name() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
