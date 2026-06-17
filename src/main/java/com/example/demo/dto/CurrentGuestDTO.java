package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class CurrentGuestDTO {
    
    private String name;
    
    @JsonProperty("checkout_date")
    private LocalDate checkoutDate;

    public CurrentGuestDTO() {
    }

    public CurrentGuestDTO(String name, LocalDate checkoutDate) {
        this.name = name;
        this.checkoutDate = checkoutDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
