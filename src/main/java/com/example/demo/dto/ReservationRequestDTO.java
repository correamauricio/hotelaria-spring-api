package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationRequestDTO {

    @NotNull(message = "Guest ID cannot be null")
    private Long guestId;

    @NotNull(message = "Room ID cannot be null")
    private Long roomId;

    @NotNull(message = "Check-in date cannot be null")
    private LocalDate checkinDate;

    @NotNull(message = "Check-out date cannot be null")
    private LocalDate checkoutDate;

    private BigDecimal totalAmount;

    @AssertTrue(message = "Check-out date must be after Check-in date")
    public boolean isValidDateRange() {
        if (checkinDate == null || checkoutDate == null) {
            return true; // Let @NotNull handle the null cases
        }
        return checkoutDate.isAfter(checkinDate);
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
}
