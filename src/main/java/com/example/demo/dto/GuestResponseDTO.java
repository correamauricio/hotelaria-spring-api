package com.example.demo.dto;

import com.example.demo.model.Guest;
import java.time.LocalDate;

public class GuestResponseDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;

    private LocalDate birthDate;

    public GuestResponseDTO() {
    }

    public GuestResponseDTO(Guest guest) {
        this.id = guest.getGuestId();
        this.name = guest.getFullName();
        this.cpf = guest.getDocumentNumber();
        this.email = guest.getEmail();
        this.birthDate = guest.getBirthDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
