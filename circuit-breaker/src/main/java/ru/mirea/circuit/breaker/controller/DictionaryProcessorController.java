package ru.mirea.circuit.breaker.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.mirea.auth.AuthRole;
import ru.mirea.auth.Role;
import ru.mirea.circuit.breaker.entity.util.RequestProcessingResult;
import ru.mirea.circuit.breaker.service.CircuitBreakerService;
import ru.mirea.dto.DictionaryOptionsDto;
import ru.mirea.dto.DictionaryRequestDto;
import ru.mirea.dto.DictionaryResponseDto;
import ru.mirea.service.UtilService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/circuit-breaker/processing/dictionary")
@RestController
public class DictionaryProcessorController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${hostname.dictionary}")
    private String dictionaryServiceHost;

    @Value("${hostname.auth}")
    private String authServiceHost;

    private final CircuitBreakerService circuitBreakerService;

    @AuthRole(Role.SYSTEM)
    @PostMapping("/info")
    public ResponseEntity<DictionaryResponseDto> processInfoRequest(@RequestBody DictionaryRequestDto requestDto, HttpServletRequest request) {
        UtilService.logRequest(applicationName);
        String token = UtilService.getTokenFromAuthServiceLogin(authServiceHost, applicationName, applicationName, UtilService.readPassword());

        RequestProcessingResult processingResult = circuitBreakerService.tryProcessRequest(request);
        if (!processingResult.getIsValid())
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(BAD_REQUEST, processingResult.getDescription())).build();
        if (!processingResult.getIsAllowed()) return ResponseEntity.ok(generateCircuitBreakerResponseDto());

        var dictionaryServiceResponse = RestClient.create().post()
                .uri(dictionaryServiceHost + "/dictionary/info")
                .header(HttpHeaders.AUTHORIZATION, token)
                .header("request-from", applicationName)
                .header("request-to", "dictionary")
                .body(requestDto)
                .retrieve()
                .onStatus(status -> status.value() == 400, (req, res) -> {
                })
                .body(DictionaryResponseDto.class);

        return ResponseEntity.ok(dictionaryServiceResponse);
    }

    @AuthRole(Role.SYSTEM)
    @GetMapping("/options")
    public ResponseEntity<DictionaryOptionsDto> processOptionsRequest(HttpServletRequest request) {
        UtilService.logRequest(applicationName);
        String token = UtilService.getTokenFromAuthServiceLogin(authServiceHost, applicationName, applicationName, UtilService.readPassword());

        RequestProcessingResult processingResult = circuitBreakerService.tryProcessRequest(request);
        if (!processingResult.getIsValid()) return ResponseEntity.of(ProblemDetail.forStatusAndDetail(BAD_REQUEST, processingResult.getDescription())).build();
        if (!processingResult.getIsAllowed()) return ResponseEntity.ok(generateCircuitBreakerOptionsDto());

        var dictionaryServiceResponse = RestClient.create().get()
                .uri(dictionaryServiceHost + "/dictionary/options")
                .header(HttpHeaders.AUTHORIZATION, token)
                .header("request-from", applicationName)
                .header("request-to", "dictionary")
                .retrieve()
                .onStatus(status -> status.value() == 400, (req, res) -> {
                })
                .body(DictionaryOptionsDto.class);

        return ResponseEntity.ok(dictionaryServiceResponse);
    }

    private DictionaryResponseDto generateCircuitBreakerResponseDto() {
        return DictionaryResponseDto.builder()
                .value(null)
                .description(String.format(
                        "Сервис справочных данных временно недоступен. Запрос обработан сервисом управления трафика в %s",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())))
                .build();
    }

    private DictionaryOptionsDto generateCircuitBreakerOptionsDto() {
        return DictionaryOptionsDto.builder()
                .options(null)
                .description(String.format(
                        "Сервис справочных данных временно недоступен. Запрос обработан сервисом управления трафика в %s",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())))
                .build();
    }
}
