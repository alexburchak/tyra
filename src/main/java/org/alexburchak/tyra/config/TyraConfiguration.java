package org.alexburchak.tyra.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author alexburchak
 */
@Configuration
@Getter
public class TyraConfiguration extends WebMvcConfigurerAdapter {
    @Value("${tyra.messaging.endpoint}")
    private String endpoint;
    @Value("${tyra.shortener.apiKey}")
    private String apiKey;
}
