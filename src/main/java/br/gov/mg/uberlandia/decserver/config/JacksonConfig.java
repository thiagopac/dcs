package br.gov.mg.uberlandia.decserver.config;

import br.gov.mg.uberlandia.decserver.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.defaultObjectMapper();
    }

}
