package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.model.enums.RoomStatus;
import com.example.demo.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RoomControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        roomRepository.deleteAll();
        
        Room room1 = new Room();
        room1.setNumber("101");
        room1.setStatus(RoomStatus.AVAILABLE);
        room1.setBedCount(2);
        roomRepository.save(room1);

        Room room2 = new Room();
        room2.setNumber("102");
        room2.setStatus(RoomStatus.OCCUPIED);
        room2.setBedCount(3);
        roomRepository.save(room2);
    }

    @Test
    public void getRooms_ReturnsAllRooms() throws Exception {
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].room_number", containsInAnyOrder("101", "102")));
    }

    @Test
    public void getRooms_WithStatusFilter_ReturnsFilteredRooms() throws Exception {
        mockMvc.perform(get("/api/rooms").param("status", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].room_number", is("101")));
    }

    @Test
    public void getRoomById_WhenExists_ReturnsRoomDetails() throws Exception {
        Room room = roomRepository.findAll().stream()
                .filter(r -> r.getNumber().equals("101"))
                .findFirst()
                .orElseThrow();

        mockMvc.perform(get("/api/rooms/" + room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(room.getId().intValue())))
                .andExpect(jsonPath("$.number", is("101")))
                .andExpect(jsonPath("$.status", is("AVAILABLE")));
    }

    @Test
    public void getRoomById_WhenNotExists_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/rooms/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", containsString("Quarto não encontrado")));
    }
}
