package ru.mirea.dictionary.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.dictionary.repo.DictionaryRedisRepository;
import ru.mirea.dictionary.config.RedisSchema;
import ru.mirea.dictionary.dto.OptionsDto;
import ru.mirea.dictionary.dto.RequestDto;
import ru.mirea.dictionary.dto.ResponseDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/info")
    public ResponseEntity<ResponseDto> getWeatherInfo(@RequestBody RequestDto requestDto) {
        if (!isRequestValid(requestDto)) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseDto(null, "The request failed validation"));
        }

        List<String> result = repo.getData(requestDto.getCity(), requestDto.getCondition(), requestDto.getMonth());
        return ResponseEntity.ok(ResponseDto.builder()
                .value(result.get(0))
                .description(result.get(1))
                .build());
    }

    @GetMapping("/options")
    public ResponseEntity<OptionsDto> getRequestOptions() {
        return ResponseEntity.ok(
                OptionsDto.builder()
                        .options(Map.of(
                                "city", RedisSchema.CITIES,
                                "condition", RedisSchema.CONDITIONS,
                                "month", RedisSchema.MONTHS))
                        .build()
        );
    }

    private boolean isRequestValid(RequestDto req) {
        return RedisSchema.CITIES.contains(req.getCity()) &&
                RedisSchema.CONDITIONS.contains(req.getCondition()) &&
                RedisSchema.MONTHS.contains(req.getMonth());
    }
}
