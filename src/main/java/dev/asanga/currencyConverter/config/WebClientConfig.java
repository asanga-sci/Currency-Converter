package dev.asanga.currencyConverter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient buildWebClient(){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.currencyapi.com/v3")
                .build();
        return webClient;
    }
}
