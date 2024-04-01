package ru.mirea.circuit.breaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.circuit.breaker.entity.util.StatusValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CircuitBreakerRequestDto {
    private UUID id;
    private SystemDto requestFromSystem;
    private SystemDto requestToSystem;
    private StatusValue status;
    private LocalDateTime timestamp;
}
