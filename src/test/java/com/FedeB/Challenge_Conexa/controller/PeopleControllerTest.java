package com.FedeB.Challenge_Conexa.controller;

import com.FedeB.Challenge_Conexa.controller.Swapi.PeopleController;
import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeopleController.class)
class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    @BeforeEach
    void setUp() {
        // Configurar mocks si es necesario
    }

    @Test
    void testGetAllPeople() throws Exception {
        // Arrange
        List<PeopleDetailsDto> mockPeople = List.of(
                new PeopleDetailsDto(),
                new PeopleDetailsDto()
        );
        when(peopleService.getAllPeople(anyInt())).thenReturn(mockPeople);

        // Act & Assert
        mockMvc.perform(get("/api/people?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}