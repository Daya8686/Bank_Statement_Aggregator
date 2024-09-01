package com.BankStatementAggregator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@SpringBootApplication
@EnableFeignClients
public class BsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BsaApplication.class, args);
	}

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}

	@Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.filters(new SimpleFilterProvider().setFailOnUnknownId(false));
            }
        };
	}
}
