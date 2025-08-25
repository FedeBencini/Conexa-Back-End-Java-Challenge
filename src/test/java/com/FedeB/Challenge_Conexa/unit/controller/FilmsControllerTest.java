package com.FedeB.Challenge_Conexa.unit.controller;

import com.FedeB.Challenge_Conexa.controller.Swapi.FilmsController;
import com.FedeB.Challenge_Conexa.dto.Film.FilmDetailsDto;
import com.FedeB.Challenge_Conexa.service.Swapi.FilmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para el controlador {@link FilmsController}.
 * <p>
 * Estas pruebas validan el comportamiento de los endpoints relacionados con películas,
 * incluyendo la lista completa, búsqueda por ID y búsqueda por nombre.
 */
@ExtendWith(MockitoExtension.class)
public class FilmsControllerTest {

    @Mock
    private FilmsService filmsService;

    @InjectMocks
    private FilmsController filmsController;

    private FilmDetailsDto sampleFilm;

    /**
     * Configuración inicial para las pruebas.
     */
    @BeforeEach
    public void setUp() {
        // Datos de prueba
        sampleFilm = new FilmDetailsDto();
        sampleFilm.setTitle("A New Hope");
        sampleFilm.setEpisode_id(4);
        sampleFilm.setDirector("George Lucas");
        sampleFilm.setProducer("Gary Kurtz, Rick McCallum");
        sampleFilm.setRelease_date("1977-05-25");
    }

    /**
     * Prueba el escenario de éxito al listar todas las películas.
     * <p>
     * Verifica que el endpoint /api/films retorne una lista de películas.
     */
    @Test
    public void testGetFilms_Success() {
        // Simular el comportamiento del FilmsService
        List<FilmDetailsDto> mockFilms = Arrays.asList(sampleFilm);
        when(filmsService.getAllFilms()).thenReturn(mockFilms);

        // Ejecutar el método getFilms
        ResponseEntity<List<FilmDetailsDto>> response = filmsController.getFilms();

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("A New Hope", response.getBody().get(0).getTitle());
        verify(filmsService).getAllFilms();
    }

    /**
     * Prueba el escenario de éxito al buscar una película por ID.
     * <p>
     * Verifica que el endpoint /api/films/id retorne los detalles de una película específica.
     */
    @Test
    public void testGetFilmsById_Success() {
        // Simular el comportamiento del FilmsService
        when(filmsService.getFilmsById("4")).thenReturn(sampleFilm);

        // Ejecutar el método getFilmsById
        ResponseEntity<FilmDetailsDto> response = filmsController.getFilmsById("4");

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("A New Hope", response.getBody().getTitle());
        verify(filmsService).getFilmsById("4");
    }

    /**
     * Prueba el escenario de error al buscar una película por ID inexistente.
     * <p>
     * Verifica que el endpoint lance una excepción cuando no se encuentra la película.
     */
    @Test
    public void testGetFilmsById_NotFound() {
        // Simular el comportamiento del FilmsService
        when(filmsService.getFilmsById("999")).thenThrow(new IllegalArgumentException("Film not found"));

        // Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> filmsController.getFilmsById("999"));
        assertEquals("Film not found", exception.getMessage());

        // Verificar que no haya más interacciones innecesarias
        verify(filmsService).getFilmsById("999");
    }

    /**
     * Prueba el escenario de éxito al buscar películas por nombre.
     * <p>
     * Verifica que el endpoint /api/films/name retorne las películas coincidentes.
     */
    @Test
    public void testGetFilmsByName_Success() {
        // Simular el comportamiento del FilmsService
        List<FilmDetailsDto> mockFilms = Arrays.asList(sampleFilm);
        when(filmsService.getFilmsByName("Hope")).thenReturn(mockFilms);

        // Ejecutar el método getFilmsByName
        ResponseEntity<List<FilmDetailsDto>> response = filmsController.getFilmsByName("Hope");

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("A New Hope", response.getBody().get(0).getTitle());
        verify(filmsService).getFilmsByName("Hope");
    }

    /**
     * Prueba el escenario de error al buscar películas por nombre inexistente.
     * <p>
     * Verifica que el endpoint lance una excepción cuando no se encuentran películas.
     */
    @Test
    public void testGetFilmsByName_NotFound() {
        // Simular el comportamiento del FilmsService
        when(filmsService.getFilmsByName("Unknown")).thenThrow(new IllegalArgumentException("No films found with name: Unknown"));

        // Verificar que se lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> filmsController.getFilmsByName("Unknown"));
        assertEquals("No films found with name: Unknown", exception.getMessage());

        // Verificar que no haya más interacciones innecesarias
        verify(filmsService).getFilmsByName("Unknown");
    }
}
