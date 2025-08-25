package com.FedeB.Challenge_Conexa.integration;

import com.FedeB.Challenge_Conexa.integration.Requests.SwapiClient;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SwapiClientIntegrationTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SwapiClient swapiClient;

    /*
    @Test
    void testGetPeople() {
        // Arrange
        PeopleDetailsDto person = new PeopleDetailsDto();
        person.setName("Luke Skywalker");

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                ArgumentMatchers.<ParameterizedTypeReference<SwapiResponse<PeopleDetailsDto>>>any()
        )).thenReturn(ResponseEntity.ok(new SwapiResponse<>(
                82,
                "https://www.swapi.tech/api/people/?page=2",
                null,
                List.of(person))
        ));

        // Act
        var result = swapiClient.getPeople(1, "Luke");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Luke Skywalker", result.get(0).getName());
    }

    @Test
    void testGetPeople_withNullResponse_throwsException() {
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(),
                ArgumentMatchers.<ParameterizedTypeReference<SwapiResponse<PeopleDetailsDto>>>any()
        )).thenReturn(ResponseEntity.ok(null));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            swapiClient.getPeople(1, "Luke");
        });

        assertEquals("Falló la obtención de data desde SWAPI: Cuerpo de respuesta es null.", exception.getMessage());
    }
    */
}
