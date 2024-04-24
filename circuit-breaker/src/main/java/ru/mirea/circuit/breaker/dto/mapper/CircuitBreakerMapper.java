package ru.mirea.circuit.breaker.dto.mapper;

import org.springframework.lang.NonNull;
import ru.mirea.circuit.breaker.dto.AuditDto;
import ru.mirea.circuit.breaker.dto.CircuitBreakerRequestDto;
import ru.mirea.circuit.breaker.dto.PermissionDto;
import ru.mirea.circuit.breaker.dto.SystemDto;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.CircuitBreakerRequest;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.System;

public class CircuitBreakerMapper {

    public static AuditDto mapAuditToDto(Audit audit) {
        return AuditDto.builder()
                .id(audit.getId())
                .fromSystem(audit.getPermission().getRequestFromSystem().getName())
                .toSystem(audit.getPermission().getRequestToSystem().getName())
                .oldStatus(audit.getOldStatus().getValue())
                .newStatus(audit.getNewStatus().getValue())
                .username(audit.getUsername())
                .timestamp(audit.getTimestamp())
                .build();
    }

    public static CircuitBreakerRequestDto mapRequestToDto(CircuitBreakerRequest circuitBreakerRequest) {
        return CircuitBreakerRequestDto.builder()
                .id(circuitBreakerRequest.getId())
                .requestFromSystem(CircuitBreakerMapper.mapSystemToDto(circuitBreakerRequest.getRequestFromSystem()))
                .requestToSystem(CircuitBreakerMapper.mapSystemToDto(circuitBreakerRequest.getRequestToSystem()))
                .status(circuitBreakerRequest.getStatus().getValue())
                .timestamp(circuitBreakerRequest.getTimestamp())
                .build();
    }

    public static SystemDto mapSystemToDto(System system) {
        return SystemDto.builder()
                .id(system.getId())
                .name(system.getName())
                .registrationTime(system.getRegistrationTime())
                .registrationType(system.getRegistrationType())
                .build();
    }

    public static PermissionDto mapPermissionToDto(@NonNull Permission permission) {
        if (permission.getId() == null) {
            return new PermissionDto();
        }

        return PermissionDto.builder()
                .id(permission.getId())
                .requestFromSystem(permission.getRequestFromSystem().getName())
                .requestToSystem(permission.getRequestToSystem().getName())
                .status(permission.getStatus().getValue())
                .build();
    }
}
