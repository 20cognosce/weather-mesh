package ru.mirea.circuit.breaker.dto.mapper;

import ru.mirea.circuit.breaker.dto.AuditDto;
import ru.mirea.circuit.breaker.dto.PermissionDto;
import ru.mirea.circuit.breaker.dto.SystemDto;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.System;

public class CircuitBreakerMapper {

    public static AuditDto mapAuditToDto(Audit audit) {
        return AuditDto.builder()
                .id(audit.getId())
                .oldStatus(audit.getOldStatus().getValue())
                .newStatus(audit.getNewStatus().getValue())
                .permission(mapPermissionToDto(audit.getPermission()))
                .userAgent(audit.getUserAgent())
                .timestamp(audit.getTimestamp())
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

    public static PermissionDto mapPermissionToDto(Permission permission) {
        return PermissionDto.builder()
                .id(permission.getId())
                .requestFromSystem(permission.getRequestFromSystem().getName())
                .requestToSystem(permission.getRequestToSystem().getName())
                .status(permission.getStatus().getValue())
                .build();
    }
}
