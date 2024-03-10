package ru.mirea.weather.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequestMapping("/weather")
@RestController
public class WeatherController {

    @Value("${hostname.dictionary}")
    private String dictionaryServiceHost;

    @GetMapping("/health")
    public ResponseEntity<String> getHealthCheck() {
        String healthCheckMessage = String.format(
                "Weather service is up at %s",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return ResponseEntity.ok(healthCheckMessage);
    }

    @GetMapping("/current")
    public ResponseEntity<String> getWeather() {
        RestClient restClient = RestClient.create();

        String dictionaryServiceReply = restClient.get()
                .uri(dictionaryServiceHost + "/dictionary/info")
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok("Info got from dict service: " + dictionaryServiceReply);
    }
}
