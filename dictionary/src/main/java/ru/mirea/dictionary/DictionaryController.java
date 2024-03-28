package ru.mirea.dictionary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.dictionary.dto.RequestDto;
import ru.mirea.dictionary.dto.ResponseDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequestMapping("/dictionary")
@RestController
public class DictionaryController {
    private final DictionaryRedisRepository repo;

    public DictionaryController(DictionaryRedisRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/health")
    public ResponseEntity<String> getHealthCheck() {
        String healthCheckMessage = String.format(
                "Dictionary service is up at %s",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return ResponseEntity.ok(healthCheckMessage);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto> getWeatherInfo(@RequestBody RequestDto requestDto) {
        if (!isRequestValid(requestDto)) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseDto.builder()
                            .description("The request failed validation")
                            .build());
        }

        List<String> result = repo.getData(requestDto.getCity(), requestDto.getType(), requestDto.getMonth());

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .value(result.get(0))
                        .description(result.get(1))
                        .build());
    }

    private boolean isRequestValid(RequestDto req) {
        var cities = List.of("Moscow", "SaintPetersburg", "Kazan", "Omsk", "Vladivostok", "Novorossiysk");
        var types = List.of("Temperature", "Precipitation", "Sunshine");
        var months = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        return cities.contains(req.getCity()) && types.contains(req.getType()) && months.contains(req.getMonth());
    }
}
