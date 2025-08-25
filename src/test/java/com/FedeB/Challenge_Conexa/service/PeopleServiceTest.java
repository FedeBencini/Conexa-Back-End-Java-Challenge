package com.FedeB.Challenge_Conexa.service;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import com.FedeB.Challenge_Conexa.service.Swapi.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PeopleServiceTest {

    @Mock
    private SwapiClient swapiClient;

    @InjectMocks
    private PeopleService peopleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPeople() {
        // Arrange
        List<PeopleDetailsDto> mockPeople = List.of(
                new PeopleDetailsDto(),
                new PeopleDetailsDto()
        );
        when(swapiClient.getPeople(anyInt())).thenReturn(mockPeople);

        // Act
        List<PeopleDetailsDto> result = peopleService.getAllPeople(1);

        // Assert
        assertEquals(2, result.size());
        verify(swapiClient, times(1)).getPeople(1);
    }

    @Test
    void testGetPersonById() {
        // Arrange
        PeopleDetailsDto mockPerson = new PeopleDetailsDto();
        when(swapiClient.getPersonById("1")).thenReturn(mockPerson);

        // Act
        PeopleDetailsDto result = peopleService.getPeopleById("1");

        // Assert
        assertEquals("Luke", result.getName());
        verify(swapiClient, times(1)).getPersonById("1");
    }
}