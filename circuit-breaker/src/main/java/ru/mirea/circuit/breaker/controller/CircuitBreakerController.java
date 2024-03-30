package ru.mirea.circuit.breaker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.circuit.breaker.dto.AuditDto;
import ru.mirea.circuit.breaker.dto.PermissionDto;
import ru.mirea.circuit.breaker.dto.SystemDto;
import ru.mirea.circuit.breaker.dto.mapper.CircuitBreakerMapper;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.System;
import ru.mirea.circuit.breaker.repo.AuditRepository;
import ru.mirea.circuit.breaker.repo.PermissionRepository;
import ru.mirea.circuit.breaker.repo.SystemRepository;
import ru.mirea.circuit.breaker.service.CircuitBreakerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequestMapping("/circuit-breaker")
@RestController
public class CircuitBreakerController {

    private final CircuitBreakerService circuitBreakerService;
    private final AuditRepository auditRepository;
    private final SystemRepository systemRepository;
    private final PermissionRepository permissionRepository;

    public CircuitBreakerController(CircuitBreakerService circuitBreakerService,
                                    AuditRepository auditRepository,
                                    SystemRepository systemRepository,
                                    PermissionRepository permissionRepository) {
        this.circuitBreakerService = circuitBreakerService;
        this.auditRepository = auditRepository;
        this.systemRepository = systemRepository;
        this.permissionRepository = permissionRepository;
    }

    @GetMapping("/health")
    public ResponseEntity<String> getHealthCheck() {
        String healthCheckMessage = String.format(
                "Circuit Breaker service is up at %s",
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return ResponseEntity.ok(healthCheckMessage);
    }

    @PatchMapping("/permissions")
    public ResponseEntity<PermissionDto> updatePermission(@RequestBody PermissionDto permissionDto, HttpServletRequest request) {
        Permission saved;

        try {
            saved = circuitBreakerService.updatePermission(permissionDto, request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .of(ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Unable to update permission: incorrect parameter found"))
                    .build();
        }

        PermissionDto savedDto = CircuitBreakerMapper.mapPermissionToDto(saved);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionDto> permissionsDto = permissions.stream()
                .map(CircuitBreakerMapper::mapPermissionToDto)
                .toList();

        return ResponseEntity.ok(permissionsDto);
    }

    @GetMapping("/systems")
    public ResponseEntity<List<SystemDto>> getAllSystems() {
        List<System> systems = systemRepository.findAll();
        List<SystemDto> systemDto = systems.stream()
                .map(CircuitBreakerMapper::mapSystemToDto)
                .toList();

        return ResponseEntity.ok(systemDto);
    }

    @GetMapping("/audit")
    public ResponseEntity<List<AuditDto>> getAllAuditEvents() {
        List<Audit> auditEvents = auditRepository.findAll();
        List<AuditDto> auditEventsDto = auditEvents.stream()
                .map(CircuitBreakerMapper::mapAuditToDto)
                .toList();

        return ResponseEntity.ok(auditEventsDto);
    }
}
