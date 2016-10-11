package org.alexburchak.tyra.config;

import org.alexburchak.tyra.filter.MDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alexburchak
 */
@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean mdc(MDCFilter mdc) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(mdc);
        registration.addUrlPatterns("/*");
        registration.setName("mdc");
        registration.setOrder(1);
        return registration;
    }
}
