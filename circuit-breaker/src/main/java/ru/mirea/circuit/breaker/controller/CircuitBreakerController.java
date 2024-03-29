package ru.mirea.circuit.breaker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequestMapping("/circuit-breaker")
@RestController
public class CircuitBreakerController {

    @GetMapping("/health")
    public ResponseEntity<String> getHealthCheck() {
        String healthCheckMessage = String.format(
                "Circuit Breaker service is up at %s",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return ResponseEntity.ok(healthCheckMessage);
    }
}
