package ru.mirea.circuit.breaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.circuit.breaker.entity.util.StatusValue;

import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private UUID id;
    private String requestFromSystem;
    private String requestToSystem;
    private StatusValue status;
}
