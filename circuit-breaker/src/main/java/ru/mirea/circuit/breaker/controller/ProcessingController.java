package ru.mirea.circuit.breaker.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.circuit.breaker.service.CircuitBreakerService;

@Slf4j
@RestController
public class ProcessingController {
    private final CircuitBreakerService circuitBreakerService;

    public ProcessingController(CircuitBreakerService circuitBreakerService) {
        this.circuitBreakerService = circuitBreakerService;
    }

    @SneakyThrows
    @PostMapping("/break")
    public ResponseEntity getBreak(HttpServletRequest request) {

        return ResponseEntity.ok("");
    }
}
