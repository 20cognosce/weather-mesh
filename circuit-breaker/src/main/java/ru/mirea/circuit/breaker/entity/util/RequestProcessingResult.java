package ru.mirea.circuit.breaker.entity.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestProcessingResult {
    Boolean isValid;
    Boolean isAllowed;
    String description;
}
