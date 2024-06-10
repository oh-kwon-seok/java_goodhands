package com.springboot.java_eco.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration

public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion){

        Info info = new Info()
                .title("타이틀 입력")
                .version(springdocVersion)
                .description("API 설명");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}