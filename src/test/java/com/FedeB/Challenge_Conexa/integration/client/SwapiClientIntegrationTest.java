package com.FedeB.Challenge_Conexa.integration.client;

import com.FedeB.Challenge_Conexa.dto.Film.FilmDetailsDto;
import com.FedeB.Challenge_Conexa.dto.People.PeopleDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Starship.StarshipDetailsDto;
import com.FedeB.Challenge_Conexa.dto.Vehicle.VehicleDetailsDto;
import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración para el cliente {@link SwapiClient}.
 * <p>
 * Estas pruebas validan que el cliente interactúa correctamente con la API de SWAPI,
 * incluyendo la obtención de datos sobre personajes, películas, naves espaciales y vehículos.
 */
@SpringBootTest
public class SwapiClientIntegrationTest {

    @Autowired
    private SwapiClient swapiClient;

    /**
     * Configuración inicial antes de cada prueba.
     * <p>
     * Verifica que el cliente esté correctamente inyectado.
     */
    @BeforeEach
    void setUp() {
        assertNotNull(swapiClient, "El cliente SwapiClient no debe ser nulo");
    }

    /*-------------------*/
    /* PERSONAS (PEOPLE) */
    /*-------------------*/

    /**
     * Prueba la obtención de una lista paginada de personas/personajes.
     * <p>
     * Verifica que:
     * - La respuesta contenga una lista de personas.
     * - Cada persona tenga detalles válidos.
     */
    @Test
    void testGetPeople() {
        List<PeopleDetailsDto> people = swapiClient.getPeople(1);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(people);
        assertFalse(people.isEmpty());

        // Verificar que cada persona tenga un nombre y url válidos
        for (PeopleDetailsDto person : people) {
            assertNotNull(person.getName());
            assertNotNull(person.getUrl());
        }
    }

    /**
     * Prueba la obtención de los detalles de una persona por su ID.
     * <p>
     * Verifica que:
     * - Los detalles de la persona sean válidos.
     */
    @Test
    void testGetPersonById() {
        String id = "1"; // ID de Luke Skywalker
        PeopleDetailsDto person = swapiClient.getPersonById(id);

        // Verificar que la persona no sea nula
        assertNotNull(person);

        // Verificar detalles específicos de la persona
        assertEquals("Luke Skywalker", person.getName());
        assertNotNull(person.getGender());
        assertNotNull(person.getBirth_year());
    }

    /**
     * Prueba la búsqueda de personas por nombre.
     * <p>
     * Verifica que:
     * - La búsqueda devuelva resultados coincidentes.
     */
    @Test
    void testGetPersonByName() {
        String name = "Luke";
        List<PeopleDetailsDto> people = swapiClient.getPersonByName(name);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(people);
        assertFalse(people.isEmpty());

        // Verificar que los resultados coincidan con el término de búsqueda
        for (PeopleDetailsDto person : people) {
            assertTrue(person.getName().toLowerCase().contains(name.toLowerCase()));
        }
    }

    /*-------------------*/
    /* PELÍCULAS (FILMS) */
    /*-------------------*/

    /**
     * Prueba la obtención de una lista de películas.
     * <p>
     * Verifica que:
     * - La respuesta contenga una lista de películas.
     * - Cada película tenga detalles válidos.
     */
    @Test
    void testGetFilms() {
        List<FilmDetailsDto> films = swapiClient.getFilms();

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(films);
        assertFalse(films.isEmpty());

        // Verificar que cada película tenga un título e Id de episodio válidos
        for (FilmDetailsDto film : films) {
            assertNotNull(film.getTitle());
            assertNotNull(film.getEpisode_id());
        }
    }

    /**
     * Prueba la obtención de los detalles de una película por su ID.
     * <p>
     * Verifica que:
     * - Los detalles de la película sean válidos.
     */
    @Test
    void testGetFilmsById() {
        String id = "1"; // ID de "A New Hope"
        FilmDetailsDto film = swapiClient.getFilmsById(id);

        // Verificar que la película no sea nula
        assertNotNull(film);

        // Verificar detalles específicos de la película
        assertEquals("A New Hope", film.getTitle());
        assertNotNull(film.getDirector());
        assertNotNull(film.getRelease_date());
    }

    /**
     * Prueba la búsqueda de películas por nombre.
     * <p>
     * Verifica que:
     * - La búsqueda devuelva resultados coincidentes.
     */
    @Test
    void testGetFilmsByName() {
        String name = "Hope";
        List<FilmDetailsDto> films = swapiClient.getFilmsByName(name);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(films);
        assertFalse(films.isEmpty());

        // Verificar que los resultados coincidan con el término de búsqueda
        for (FilmDetailsDto film : films) {
            assertTrue(film.getTitle().toLowerCase().contains(name.toLowerCase()));
        }
    }

    /*-------------------*/
    /* NAVES (STARSHIPS) */
    /*-------------------*/

    /**
     * Prueba la obtención de una lista paginada de naves espaciales.
     * <p>
     * Verifica que:
     * - La respuesta contenga una lista de naves.
     * - Cada nave tenga detalles válidos.
     */
    @Test
    void testGetStarships() {
        List<StarshipDetailsDto> starships = swapiClient.getStarships(1);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(starships);
        assertFalse(starships.isEmpty());

        // Verificar que cada nave tenga un nombre y modelo válidos
        for (StarshipDetailsDto starship : starships) {
            assertNotNull(starship.getName());
            assertNotNull(starship.getModel());
        }
    }

    /**
     * Prueba la obtención de los detalles de una nave por su ID.
     * <p>
     * Verifica que:
     * - Los detalles de la nave sean válidos.
     */
    @Test
    void testGetStarshipsById() {
        String id = "12"; // ID de "X-wing"
        StarshipDetailsDto starship = swapiClient.getStarshipsById(id);

        // Verificar que la nave no sea nula
        assertNotNull(starship);

        // Verificar detalles específicos de la nave
        assertEquals("X-wing", starship.getName());
        assertNotNull(starship.getStarship_class());
        assertNotNull(starship.getManufacturer());
    }

    /**
     * Prueba la búsqueda de naves por nombre.
     * <p>
     * Verifica que:
     * - La búsqueda devuelva resultados coincidentes.
     */
    @Test
    void testGetStarshipsByName() {
        String name = "X-wing";
        List<StarshipDetailsDto> starships = swapiClient.getStarshipsByName(name);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(starships);
        assertFalse(starships.isEmpty());

        // Verificar que los resultados coincidan con el término de búsqueda
        for (StarshipDetailsDto starship : starships) {
            assertTrue(starship.getName().toLowerCase().contains(name.toLowerCase()));
        }
    }

    /*----------------------*/
    /* VEHÍCULOS (VEHICLES) */
    /*----------------------*/

    /**
     * Prueba la obtención de una lista paginada de vehículos.
     * <p>
     * Verifica que:
     * - La respuesta contenga una lista de vehículos.
     * - Cada vehículo tenga detalles válidos.
     */
    @Test
    void testGetVehicles() {
        List<VehicleDetailsDto> vehicles = swapiClient.getVehicles(1);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(vehicles);
        assertFalse(vehicles.isEmpty());

        // Verificar que cada vehículo tenga un nombre y modelo válidos
        for (VehicleDetailsDto vehicle : vehicles) {
            assertNotNull(vehicle.getName());
            assertNotNull(vehicle.getModel());
        }
    }

    /**
     * Prueba la obtención de los detalles de un vehículo por su ID.
     * <p>
     * Verifica que:
     * - Los detalles del vehículo sean válidos.
     */
    @Test
    void testGetVehiclesById() {
        String id = "4"; // ID de "Sand Crawler"
        VehicleDetailsDto vehicle = swapiClient.getVehiclesById(id);

        // Verificar que el vehículo no sea nulo
        assertNotNull(vehicle);

        // Verificar detalles específicos del vehículo
        assertEquals("Sand Crawler", vehicle.getName());
        assertNotNull(vehicle.getVehicle_class());
        assertNotNull(vehicle.getManufacturer());
    }

    /**
     * Prueba la búsqueda de vehículos por nombre.
     * <p>
     * Verifica que:
     * - La búsqueda devuelva resultados coincidentes.
     */
    @Test
    void testGetVehiclesByName() {
        String name = "Sand";
        List<VehicleDetailsDto> vehicles = swapiClient.getVehiclesByName(name);

        // Verificar que la lista no sea nula ni vacía
        assertNotNull(vehicles);
        assertFalse(vehicles.isEmpty());

        // Verificar que los resultados coincidan con el término de búsqueda
        for (VehicleDetailsDto vehicle : vehicles) {
            assertTrue(vehicle.getName().toLowerCase().contains(name.toLowerCase()));
        }
    }
}