package com.FedeB.Challenge_Conexa.unit.controller;

import com.FedeB.Challenge_Conexa.controller.Swapi.PeopleController;
import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.PeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el controlador {@link PeopleController}.
 * <p>
 * Estas pruebas validan el comportamiento de los endpoints relacionados con personas,
 * incluyendo la lista completa, búsqueda por ID y búsqueda por nombre.
 */
@ExtendWith(MockitoExtension.class)
public class PeopleControllerTest {

    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

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
     * Prueba el escenario de éxito al listar todas las personas sin paginación.
     * <p>
     * Verifica que el endpoint /api/people retorne una lista de personas.
     */
    @Test
    public void testGetPeople_Success() {
        // Simular el comportamiento del PeopleService
        List<PeopleDetailsDto> mockPeople = Arrays.asList(samplePerson);
        when(peopleService.getAllPeople(null)).thenReturn(mockPeople);

        // Ejecutar el método getPeople
        ResponseEntity<List<PeopleDetailsDto>> response = peopleController.getPeople(null);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Luke Skywalker", response.getBody().get(0).getName());
        verify(peopleService).getAllPeople(null);
    }

    /**
     * Prueba el escenario de éxito al listar todas las personas con paginación.
     * <p>
     * Verifica que el endpoint /api/people retorne una lista de personas para una página específica.
     */
    @Test
    public void testGetPeople_WithPagination() {
        // Simular el comportamiento del PeopleService
        List<PeopleDetailsDto> mockPeople = Arrays.asList(samplePerson);
        when(peopleService.getAllPeople(2)).thenReturn(mockPeople);

        // Ejecutar el método getPeople
        ResponseEntity<List<PeopleDetailsDto>> response = peopleController.getPeople(2);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Luke Skywalker", response.getBody().get(0).getName());
        verify(peopleService).getAllPeople(2);
    }

    /**
     * Prueba el escenario de éxito al buscar una persona por ID.
     * <p>
     * Verifica que el endpoint /api/people/id retorne los detalles de una persona específica.
     */
    @Test
    public void testGetPeopleById_Success() {
        // Simular el comportamiento del PeopleService
        when(peopleService.getPeopleById("1")).thenReturn(samplePerson);

        // Ejecutar el método getPeopleById
        ResponseEntity<PeopleDetailsDto> response = peopleController.getPeopleById("1");

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Luke Skywalker", response.getBody().getName());
        verify(peopleService).getPeopleById("1");
    }

    /**
     * Prueba el escenario de error al buscar una persona por ID inexistente.
     * <p>
     * Verifica que el endpoint lance una excepción cuando no se encuentra la persona.
     */
    @Test
    public void testGetPeopleById_NotFound() {
        // Simular el comportamiento del PeopleService
        when(peopleService.getPeopleById("999")).thenThrow(new IllegalArgumentException("Person not found"));

        // Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> peopleController.getPeopleById("999"));
        assertEquals("Person not found", exception.getMessage());

        // Verificar que no haya más interacciones innecesarias
        verify(peopleService).getPeopleById("999");
    }

    /**
     * Prueba el escenario de éxito al buscar personas por nombre.
     * <p>
     * Verifica que el endpoint /api/people/name retorne las personas coincidentes.
     */
    @Test
    public void testGetPeopleByName_Success() {
        // Simular el comportamiento del PeopleService
        List<PeopleDetailsDto> mockPeople = Arrays.asList(samplePerson);
        when(peopleService.getPeopleByName("Luke")).thenReturn(mockPeople);

        // Ejecutar el método getPeopleByName
        ResponseEntity<List<PeopleDetailsDto>> response = peopleController.getPeopleByName("Luke");

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Luke Skywalker", response.getBody().get(0).getName());
        verify(peopleService).getPeopleByName("Luke");
    }

    /**
     * Prueba el escenario de error al buscar personas por nombre inexistente.
     * <p>
     * Verifica que el endpoint lance una excepción cuando no se encuentran personas.
     */
    @Test
    public void testGetPeopleByName_NotFound() {
        // Simular el comportamiento del PeopleService
        when(peopleService.getPeopleByName("Unknown")).thenThrow(new IllegalArgumentException("No people found with name: Unknown"));

        // Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> peopleController.getPeopleByName("Unknown"));
        assertEquals("No people found with name: Unknown", exception.getMessage());

        // Verificar que no haya más interacciones innecesarias
        verify(peopleService).getPeopleByName("Unknown");
    }
}