package ru.mirea.dictionary.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.dictionary.repo.DictionaryRedisRepository;
import ru.mirea.dictionary.config.RedisSchema;
import ru.mirea.dto.OptionsDto;
import ru.mirea.dto.RequestDto;
import ru.mirea.dto.ResponseDto;
import ru.mirea.service.UtilService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/dictionary")
@RestController
public class DictionaryController {

    private final DictionaryRedisRepository repo;

    @GetMapping("/health")
    public ResponseEntity<String> getHealthCheck() {
        String healthCheckMessage = String.format(
                "Dictionary service is up at %s",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return ResponseEntity.ok(healthCheckMessage);
    }

    @PostMapping("/info")
    public ResponseEntity<ResponseDto> getWeatherInfo(@RequestBody RequestDto requestDto, HttpServletRequest request) {
        logRequest(request);

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
    public ResponseEntity<OptionsDto> getRequestOptions(HttpServletRequest request) {
        logRequest(request);

        return ResponseEntity.ok(
                OptionsDto.builder()
                        .options(Map.of(
                                "city", RedisSchema.CITIES,
                                "condition", RedisSchema.CONDITIONS,
                                "month", RedisSchema.MONTHS))
                        .description("Перечень доступных значений для параметров запроса /info сервиса справочных данных")
                        .build()
        );
    }

    private boolean isRequestValid(RequestDto req) {
        return RedisSchema.CITIES.contains(req.getCity()) &&
                RedisSchema.CONDITIONS.contains(req.getCondition()) &&
                RedisSchema.MONTHS.contains(req.getMonth());
    }

    private void logRequest(HttpServletRequest request) {
        log.info(String.format(
                "Dictionary service received new request: %s",
                UtilService.extractRequestInfo(request)));
    }
}
