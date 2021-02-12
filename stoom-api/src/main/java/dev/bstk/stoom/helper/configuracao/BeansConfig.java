package dev.bstk.stoom.helper.configuracao;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public ModelMapper mapper() {
        final var modelMapper = new ModelMapper();
        modelMapper
            .getConfiguration()
            .setFieldMatchingEnabled(true);

        return modelMapper;
    }

}
