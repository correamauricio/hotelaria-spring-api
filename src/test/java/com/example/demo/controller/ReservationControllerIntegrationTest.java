package com.example.demo.controller;

import com.example.demo.dto.ReservationRequestDTO;
import com.example.demo.model.Guest;
import com.example.demo.model.Room;
import com.example.demo.model.enums.RoomStatus;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.model.Reservation;
import com.example.demo.model.enums.ReservationStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReservationControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    private Guest savedGuest;
    private Room savedRoom;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        reservationRepository.deleteAll();
        guestRepository.deleteAll();
        roomRepository.deleteAll();

        Guest guest = new Guest();
        guest.setFullName("Roberto Silva");
        guest.setDocumentNumber("999.888.777-66");
        guest.setEmail("roberto@silva.com");
        guest.setBirthDate(LocalDate.of(1980, 12, 12));
        savedGuest = guestRepository.save(guest);

        Room room = new Room();
        room.setNumber("305");
        room.setStatus(RoomStatus.AVAILABLE);
        room.setBedCount(1);
        savedRoom = roomRepository.save(room);
    }

    @Test
    public void createReservation_WithValidData_ReturnsCreated() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setGuestId(savedGuest.getGuestId());
        request.setRoomId(savedRoom.getId());
        request.setCheckinDate(LocalDate.now().plusDays(1));
        request.setCheckoutDate(LocalDate.now().plusDays(5));
        request.setTotalAmount(new BigDecimal("500.00"));

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.guest_id", is(savedGuest.getGuestId().intValue())))
                .andExpect(jsonPath("$.room_id", is(savedRoom.getId().intValue())))
                .andExpect(jsonPath("$.status", is("SCHEDULED")));
                
        // Verify room status changed to RESERVED (assuming that's your business rule)
        // Or if the rule is it stays AVAILABLE until check-in, we could assert that.
    }

    @Test
    public void createReservation_WithOccupiedRoom_ReturnsConflict() throws Exception {
        Reservation conflicting = new Reservation();
        conflicting.setGuest(savedGuest);
        conflicting.setRoom(savedRoom);
        conflicting.setExpectedCheckInDate(LocalDate.now());
        conflicting.setExpectedCheckOutDate(LocalDate.now().plusDays(10));
        conflicting.setAppliedDailyRate(new BigDecimal("100.00"));
        conflicting.setTotalAmount(new BigDecimal("1000.00"));
        conflicting.setStatus(ReservationStatus.IN_PROGRESS);
        reservationRepository.save(conflicting);

        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setGuestId(savedGuest.getGuestId());
        request.setRoomId(savedRoom.getId());
        request.setCheckinDate(LocalDate.now().plusDays(1));
        request.setCheckoutDate(LocalDate.now().plusDays(5));
        request.setTotalAmount(new BigDecimal("500.00"));

        mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", containsString("occupied or reserved")));
    }

    @Test
    public void doCheckIn_WhenScheduled_ReturnsInProgress() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setGuest(savedGuest);
        reservation.setRoom(savedRoom);
        reservation.setExpectedCheckInDate(LocalDate.now());
        reservation.setExpectedCheckOutDate(LocalDate.now().plusDays(2));
        reservation.setAppliedDailyRate(new BigDecimal("100.00"));
        reservation.setTotalAmount(new BigDecimal("200.00"));
        reservation.setStatus(ReservationStatus.SCHEDULED);
        reservation = reservationRepository.save(reservation);

        mockMvc.perform(post("/api/reservations/" + reservation.getId() + "/checkin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")));
    }

    @Test
    public void doCheckOut_WhenInProgress_ReturnsCompleted() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setGuest(savedGuest);
        reservation.setRoom(savedRoom);
        reservation.setExpectedCheckInDate(LocalDate.now().minusDays(2));
        reservation.setExpectedCheckOutDate(LocalDate.now());
        reservation.setAppliedDailyRate(new BigDecimal("100.00"));
        reservation.setTotalAmount(new BigDecimal("200.00"));
        reservation.setStatus(ReservationStatus.IN_PROGRESS);
        reservation = reservationRepository.save(reservation);

        mockMvc.perform(post("/api/reservations/" + reservation.getId() + "/checkout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("COMPLETED")));
    }
}
