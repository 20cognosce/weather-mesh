package ru.mirea.circuit.breaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SystemDto {
    private UUID id;
    private String name;
    private LocalDateTime registrationTime;
    private String registrationType;
}
