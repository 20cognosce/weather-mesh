package ru.mirea.circuit.breaker.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.mirea.circuit.breaker.service.CircuitBreakerService;
import ru.mirea.dto.OptionsDto;
import ru.mirea.dto.RequestDto;
import ru.mirea.dto.ResponseDto;
import ru.mirea.service.UtilService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/circuit-breaker/processing")
@RestController
public class DictionaryProcessorController {

    @Value("${hostname.dictionary}")
    private String dictionaryServiceHost;

    private final CircuitBreakerService circuitBreakerService;

    @PostMapping("/dict/info")
    public ResponseEntity<ResponseDto> processInfoRequest(@RequestBody RequestDto requestDto, HttpServletRequest request) {
        logRequest(request);

        if (circuitBreakerService.tryRejectRequest(request)) {
            return ResponseEntity.ok(generateCircuitBreakerResponseDto());
        }

        var dictionaryServiceResponse = RestClient.create().post()
                .uri(dictionaryServiceHost + "/dictionary/info")
                .header("request-from", "circuit-breaker")
                .header("request-to", "dictionary")
                .body(requestDto)
                .retrieve()
                .body(ResponseDto.class);

        return ResponseEntity.ok(dictionaryServiceResponse);
    }

    @GetMapping("/dict/options")
    public ResponseEntity<OptionsDto> processOptionsRequest(HttpServletRequest request) {
        logRequest(request);

        if (circuitBreakerService.tryRejectRequest(request)) {
            return ResponseEntity.ok(generateCircuitBreakerOptionsDto());
        }

        var dictionaryServiceResponse = RestClient.create().get()
                .uri(dictionaryServiceHost + "/dictionary/options")
                .header("request-from", "circuit-breaker")
                .header("request-to", "dictionary")
                .retrieve()
                .body(OptionsDto.class);

        return ResponseEntity.ok(dictionaryServiceResponse);
    }

    private ResponseDto generateCircuitBreakerResponseDto() {
        return ResponseDto.builder()
                .value(null)
                .description(String.format(
                        "Сервис справочных данных временно недоступен. Запрос обработан сервисом управления трафика в %s",
                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))
                .build();
    }

    private OptionsDto generateCircuitBreakerOptionsDto() {
        return OptionsDto.builder()
                .options(null)
                .description(String.format(
                        "Сервис справочных данных временно недоступен. Запрос обработан сервисом управления трафика в %s",
                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))
                .build();
    }

    private void logRequest(HttpServletRequest request) {
        log.info(String.format(
                "Circuit Breaker service received new request: %s",
                UtilService.extractRequestInfo(request)));
    }
}
