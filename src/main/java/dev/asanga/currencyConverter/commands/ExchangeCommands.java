package dev.asanga.currencyConverter.commands;

import dev.asanga.currencyConverter.models.APIErrorResponse;
import dev.asanga.currencyConverter.models.APIResponse;
import dev.asanga.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class ExchangeCommands {

    private final WebClient webClient;

    private static final String API_KEY ="api_key";

    public ExchangeCommands(WebClient webClient) {
        this.webClient = webClient;
    }

    @ShellMethod(key = "convert", value = "convert one currency to another")
    public BigDecimal convert(String from, String to, BigDecimal amount){
        Map<String, Object> params = new HashMap<>();
        APIResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/latest")
                        .queryParam("base_currency", from)
                        .queryParam("currencies", to)
                        .build())
                .header("apikey",API_KEY)
                .retrieve()
                .bodyToMono(APIResponse.class)
                .block();

        BigDecimal rate = BigDecimal.valueOf((double)response.getData().get(to).get("value"));

        return amount.multiply(rate).setScale(2, RoundingMode.HALF_DOWN);
    }

    @ShellMethod(key = "status", value = "check API is online and quota")
    public void test(){
        Map<String, Object> params = new HashMap<>();
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/status")
                                .build())
                .header("apikey",API_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("API Error")
                .block();

        System.out.println(response);
    }
}
