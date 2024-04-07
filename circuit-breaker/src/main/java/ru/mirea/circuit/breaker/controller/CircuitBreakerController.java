package ru.mirea.circuit.breaker.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.auth.AuthRole;
import ru.mirea.auth.Role;
import ru.mirea.circuit.breaker.dto.AuditDto;
import ru.mirea.circuit.breaker.dto.CircuitBreakerRequestDto;
import ru.mirea.circuit.breaker.dto.PermissionDto;
import ru.mirea.circuit.breaker.dto.SystemDto;
import ru.mirea.circuit.breaker.dto.mapper.CircuitBreakerMapper;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.CircuitBreakerRequest;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.System;
import ru.mirea.circuit.breaker.repo.AuditRepository;
import ru.mirea.circuit.breaker.repo.PermissionRepository;
import ru.mirea.circuit.breaker.repo.RequestRepository;
import ru.mirea.circuit.breaker.repo.SystemRepository;
import ru.mirea.circuit.breaker.service.CircuitBreakerService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor
@RequestMapping("/circuit-breaker")
@RestController
public class CircuitBreakerController {

    private final CircuitBreakerService circuitBreakerService;
    private final AuditRepository auditRepository;
    private final RequestRepository requestRepository;
    private final SystemRepository systemRepository;
    private final PermissionRepository permissionRepository;

    @AuthRole(Role.ADMIN)
    @PatchMapping("/permissions")
    public ResponseEntity<PermissionDto> updatePermission(@RequestBody PermissionDto permissionDto, HttpServletRequest request) {
        Permission saved;

        try {
            if (permissionDto.getId() == null) throw new NoSuchElementException();
            saved = circuitBreakerService.updatePermission(permissionDto, "localhost", request); //TODO: Implement authentication via @AuthenticationPrincipal
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .of(ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Unable to update permission: incorrect parameter found"))
                    .build();
        }

        PermissionDto savedDto = CircuitBreakerMapper.mapPermissionToDto(saved);
        return ResponseEntity.ok(savedDto);
    }

    @AuthRole(Role.ADMIN)
    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionDto> permissionsDto = permissions.stream()
                .map(CircuitBreakerMapper::mapPermissionToDto)
                .toList();

        return ResponseEntity.ok(permissionsDto);
    }

    @AuthRole(Role.ADMIN)
    @GetMapping("/systems")
    public ResponseEntity<List<SystemDto>> getAllSystems() {
        List<System> systems = systemRepository.findAll();
        List<SystemDto> systemDto = systems.stream()
                .map(CircuitBreakerMapper::mapSystemToDto)
                .toList();

        return ResponseEntity.ok(systemDto);
    }

    @AuthRole(Role.ADMIN)
    @GetMapping("/audit")
    public ResponseEntity<List<AuditDto>> getAllAuditEvents() {
        List<Audit> auditEvents = auditRepository.findAll();
        List<AuditDto> auditEventsDto = auditEvents.stream()
                .map(CircuitBreakerMapper::mapAuditToDto)
                .toList();

        return ResponseEntity.ok(auditEventsDto);
    }

    @AuthRole(Role.ADMIN)
    @GetMapping("/requests")
    public ResponseEntity<List<CircuitBreakerRequestDto>> getAllRequests() {
        List<CircuitBreakerRequest> circuitBreakerRequests = requestRepository.findAll();
        List<CircuitBreakerRequestDto> requestsDto = circuitBreakerRequests.stream()
                .map(CircuitBreakerMapper::mapRequestToDto)
                .toList();

        return ResponseEntity.ok(requestsDto);
    }
}
