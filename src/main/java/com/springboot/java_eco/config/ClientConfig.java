package com.springboot.java_eco.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        System.out.println("테스트");
        return new RestTemplate();
    }

}
