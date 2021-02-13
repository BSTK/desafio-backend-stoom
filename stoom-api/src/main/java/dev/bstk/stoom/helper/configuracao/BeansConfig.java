package dev.bstk.stoom.helper.configuracao;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class BeansConfig {

    @Bean
    public RestTemplate template() {
        return new RestTemplateBuilder()
            .setConnectTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .setReadTimeout(Duration.of(5, ChronoUnit.SECONDS))
            .build();
    }

    @Bean
    public ModelMapper mapper() {
        final var modelMapper = new ModelMapper();
        modelMapper
            .getConfiguration()
            .setFieldMatchingEnabled(true);

        return modelMapper;
    }

}
