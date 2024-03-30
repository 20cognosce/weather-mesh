package ru.mirea.weather.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.mirea.weather.dto.RequestDto;

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

    @PostMapping("/info")
    public ResponseEntity<String> getDictWeatherInfo(@RequestBody RequestDto requestDto) {
        String dictionaryServiceReply = RestClient.create().post()
                .uri(dictionaryServiceHost + "/dictionary/info")
                .header("request-from", "weather")
                .header("request-to", "dictionary")
                .body(requestDto)
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(dictionaryServiceReply);
    }

    @GetMapping("/options")
    public ResponseEntity<String> getDictRequestOptions() {
        String dictionaryServiceReply = RestClient.create().get()
                .uri(dictionaryServiceHost + "/dictionary/options")
                .header("request-from", "weather")
                .header("request-to", "dictionary")
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok(dictionaryServiceReply);
    }
}
