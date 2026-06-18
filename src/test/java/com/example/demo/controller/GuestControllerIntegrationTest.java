package com.example.demo.controller;

import com.example.demo.dto.GuestRequestDTO;
import com.example.demo.repository.GuestRepository;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional // Garante o rollback do banco após cada teste
public class GuestControllerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ObjectMapper objectMapper;

    @Autowired
    private GuestRepository guestRepository;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        guestRepository.deleteAll();
    }

    @Test
    public void createGuest_WithValidData_ReturnsCreated() throws Exception {
        GuestRequestDTO guest = new GuestRequestDTO();
        guest.setName("João da Silva");
        guest.setCpf("111.444.777-35");
        guest.setEmail("joao@silva.com");
        guest.setBirthDate(LocalDate.of(1990, 1, 1));

        mockMvc.perform(post("/api/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guest)))
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("João da Silva")))
                .andExpect(jsonPath("$.cpf", is("11144477735")))
                .andExpect(jsonPath("$.email", is("joao@silva.com")));
    }

    @Test
    public void createGuest_WithInvalidCpf_ReturnsBadRequest() throws Exception {
        GuestRequestDTO guest = new GuestRequestDTO();
        guest.setName("Maria Souza");
        guest.setCpf("CPF-INVALIDO"); // CPF inválido
        guest.setEmail("maria@souza.com");
        guest.setBirthDate(LocalDate.of(1995, 5, 5));

        mockMvc.perform(post("/api/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors.cpf").exists());
    }

    @Test
    public void getGuests_ReturnsList() throws Exception {
        GuestRequestDTO guest = new GuestRequestDTO();
        guest.setName("Carlos Almeida");
        guest.setCpf("222.555.888-46");
        guest.setEmail("carlos@almeida.com");
        guest.setBirthDate(LocalDate.of(1985, 10, 10));

        // Create a guest first
        mockMvc.perform(post("/api/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guest)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/guests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("Carlos Almeida")));
    }

    @Test
    public void getGuests_WithFilter_ReturnsFilteredList() throws Exception {
        GuestRequestDTO guest1 = new GuestRequestDTO();
        guest1.setName("Alice Wonderland");
        guest1.setCpf("333.666.999-57");
        guest1.setEmail("alice@wonderland.com");
        guest1.setBirthDate(LocalDate.of(1992, 2, 2));

        mockMvc.perform(post("/api/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guest1)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/guests").param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Alice Wonderland")));
    }
}
