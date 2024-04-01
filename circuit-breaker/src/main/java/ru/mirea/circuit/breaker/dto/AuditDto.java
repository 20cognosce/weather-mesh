package ru.mirea.circuit.breaker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.circuit.breaker.entity.StatusValue;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    private UUID id;
    private StatusValue oldStatus;
    private StatusValue newStatus;
    private PermissionDto permission;
    private String userAgent;
    private LocalDateTime timestamp;
}
