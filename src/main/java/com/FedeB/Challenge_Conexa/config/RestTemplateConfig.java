package com.FedeB.Challenge_Conexa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración para el uso de RestTemplate.
 * <p>
 * Esta clase configura un bean de tipo {@link RestTemplate}, que se utiliza para realizar llamadas HTTP
 * a servicios externos, como SWAPI (Star Wars API).
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Crea y configura un bean de tipo {@link RestTemplate}.
     * <p>
     * Este método define un RestTemplate que puede ser inyectado en otros componentes de la aplicación
     * para realizar solicitudes HTTP a servicios externos.
     *
     * @return un nuevo objeto RestTemplate configurado para su uso.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
