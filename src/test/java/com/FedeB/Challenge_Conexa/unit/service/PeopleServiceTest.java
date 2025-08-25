package com.FedeB.Challenge_Conexa.unit.service;

import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import com.FedeB.Challenge_Conexa.service.Swapi.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el servicio {@link PeopleService}.
 * <p>
 * Estas pruebas validan el comportamiento de los métodos relacionados con personajes,
 * incluyendo la lista completa, búsqueda por ID y búsqueda por nombre.
 */
@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock
    private SwapiClient swapiClient;

    @InjectMocks
    private PeopleService peopleService;

    private PeopleDetailsDto samplePerson;

    /**
     * Configuración inicial para las pruebas.
     */
    @BeforeEach
    public void setUp() {
        // Datos de prueba
        samplePerson = new PeopleDetailsDto();
        samplePerson.setName("Luke Skywalker");
        samplePerson.setBirth_year("19BBY");
        samplePerson.setGender("male");
        samplePerson.setHeight("172");
        samplePerson.setMass("77");
        samplePerson.setStarships(Collections.singletonList("https://www.swapi.tech/api/starships/13"));
    }

    /**
     * Prueba el escenario de éxito al listar todas las personas.
     * <p>
     * Verifica que el método getAllPeople retorne una lista de personas.
     */
    @Test
    public void testGetAllPeople_Success() {
        // Simular el comportamiento del SwapiClient
        List<PeopleDetailsDto> mockPeople = Arrays.asList(samplePerson);
        when(swapiClient.getPeople(null)).thenReturn(mockPeople);

        // Ejecutar el método getAllPeople
        List<PeopleDetailsDto> result = peopleService.getAllPeople(null);

        // Verificar resultados
        assertEquals(1, result.size());
        assertEquals("Luke Skywalker", result.get(0).getName());
        verify(swapiClient).getPeople(null);
    }

    /**
     * Prueba el escenario de éxito al buscar una persona por ID.
     * <p>
     * Verifica que el método getPeopleById retorne los detalles de una persona específica.
     */
    @Test
    public void testGetPeopleById_Success() {
        // Simular el comportamiento del SwapiClient
        when(swapiClient.getPersonById("1")).thenReturn(samplePerson);

        // Ejecutar el método getPeopleById
        PeopleDetailsDto result = peopleService.getPeopleById("1");

        // Verificar resultados
        assertEquals("Luke Skywalker", result.getName());
        verify(swapiClient).getPersonById("1");
    }

    /**
     * Prueba el escenario de error al buscar una persona por ID inexistente.
     * <p>
     * Verifica que el método lance una excepción cuando no se encuentra la persona.
     */
    @Test
    public void testGetPeopleById_NotFound() {
        // Simular el comportamiento del SwapiClient
        when(swapiClient.getPersonById("999")).thenThrow(new IllegalArgumentException("Person not found"));

        // Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> peopleService.getPeopleById("999"));
        assertEquals("Person not found", exception.getMessage());

        // Verificar que no haya más interacciones innecesarias
        verify(swapiClient).getPersonById("999");
    }

    /**
     * Prueba el escenario de éxito al buscar personas por nombre.
     * <p>
     * Verifica que el método getPeopleByName retorne las personas coincidentes.
     */
    @Test
    public void testGetPeopleByName_Success() {
        // Simular el comportamiento del SwapiClient
        List<PeopleDetailsDto> mockPeople = Arrays.asList(samplePerson);
        when(swapiClient.getPersonByName("Luke")).thenReturn(mockPeople);

        // Ejecutar el método getPeopleByName
        List<PeopleDetailsDto> result = peopleService.getPeopleByName("Luke");

        // Verificar resultados
        assertEquals(1, result.size());
        assertEquals("Luke Skywalker", result.get(0).getName());
        verify(swapiClient).getPersonByName("Luke");
    }

    /**
     * Prueba el escenario de error al buscar personas por nombre inexistente.
     * <p>
     * Verifica que el método lance una excepción cuando no se encuentran personas.
     */
    @Test
    public void testGetPeopleByName_NotFound() {
        // Simular el comportamiento del SwapiClient
        when(swapiClient.getPersonByName("Unknown")).thenThrow(new IllegalArgumentException("No people found with name: Unknown"));

        // Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> peopleService.getPeopleByName("Unknown"));
        assertEquals("No people found with name: Unknown", exception.getMessage());

        // Verificar que no haya más interacciones innecesarias
        verify(swapiClient).getPersonByName("Unknown");
    }
}