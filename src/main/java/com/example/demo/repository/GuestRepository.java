package com.example.demo.repository;

import com.example.demo.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByFullNameContainingIgnoreCase(String fullName);
    
    boolean existsByDocumentNumber(String documentNumber);
    
    boolean existsByEmail(String email);
}
