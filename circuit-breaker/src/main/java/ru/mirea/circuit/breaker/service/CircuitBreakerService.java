package ru.mirea.circuit.breaker.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.circuit.breaker.dto.PermissionDto;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.CircuitBreakerRequest;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.Status;
import ru.mirea.circuit.breaker.entity.System;
import ru.mirea.circuit.breaker.entity.util.RequestProcessingResult;
import ru.mirea.circuit.breaker.entity.util.StatusValue;
import ru.mirea.circuit.breaker.repo.AuditRepository;
import ru.mirea.circuit.breaker.repo.PermissionRepository;
import ru.mirea.circuit.breaker.repo.RequestRepository;
import ru.mirea.circuit.breaker.repo.StatusRepository;
import ru.mirea.circuit.breaker.repo.SystemRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CircuitBreakerService {

    private final AuditRepository auditRepository;
    private final RequestRepository requestRepository;
    private final StatusRepository statusRepository;
    private final SystemRepository systemRepository;
    private final PermissionRepository permissionRepository;
    private final AuditService auditService;

    @Transactional
    public Permission updatePermission(PermissionDto permissionDto, String username, HttpServletRequest request) {
        Permission permissionForUpdate = permissionRepository.findById(permissionDto.getId()).orElseThrow(); //null id -> IllegalArgumentException
        System fromSystemForUpdate = systemRepository.findByName(permissionDto.getRequestFromSystem()).orElseThrow();
        System toSystemForUpdate = systemRepository.findByName(permissionDto.getRequestToSystem()).orElseThrow();
        Status statusForUpdate = statusRepository.findByValue(permissionDto.getStatus()).orElseThrow();

        Audit audit = auditService.generateAuditEvent(permissionForUpdate.getStatus(), statusForUpdate, permissionForUpdate, username, request);

        permissionForUpdate.setRequestFromSystem(fromSystemForUpdate);
        permissionForUpdate.setRequestToSystem(toSystemForUpdate);
        permissionForUpdate.setStatus(statusForUpdate);

        var saved = permissionRepository.save(permissionForUpdate);
        auditRepository.save(audit);
        return saved;
    }

    @Transactional
    public RequestProcessingResult tryProcessRequest(HttpServletRequest request) {
        String requestFromSystem = request.getHeader("request-from");
        String requestToSystem = request.getHeader("request-to");

        if (requestFromSystem == null || requestToSystem == null) {
            return logAndReturnInvalid("The header 'request-from' or 'request-to' is null");
        }

        List<Permission> permissionList = permissionRepository.findPermissionsBySystemNames(requestFromSystem, requestToSystem);

        if (permissionList.size() != 1) {
            return logAndReturnInvalid(String.format("No permission found for requestFromSystem: %s and requestToSystem: %s", requestFromSystem, requestToSystem));
        }

        Permission permission = permissionList.get(0);

        if (permission.getStatus().getValue() == StatusValue.FORBIDDEN) {
            saveRequest(requestFromSystem, requestToSystem, StatusValue.FORBIDDEN, permission);
            return new RequestProcessingResult(true, false, String.format("Access FORBIDDEN from %s to %s", requestFromSystem, requestToSystem));
        } else {
            saveRequest(requestFromSystem, requestToSystem, StatusValue.ALLOWED, permission);
            return new RequestProcessingResult(true, true, String.format("Access ALLOWED from %s to %s", requestFromSystem, requestToSystem));
        }
    }

    private void saveRequest(String requestFromSystem, String requestToSystem, StatusValue value, Permission permission) {
        Status status = statusRepository.findByValue(value).orElseThrow();
        System requestFrom = systemRepository.findByName(requestFromSystem).orElseThrow();
        System requestTo = systemRepository.findByName(requestToSystem).orElseThrow();

        requestRepository.save(CircuitBreakerRequest.builder()
                .requestFromSystem(requestFrom)
                .requestToSystem(requestTo)
                .status(status)
                .permission(permission)
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build());
    }

    private RequestProcessingResult logAndReturnInvalid(String message) {
        log.warn(message);
        return new RequestProcessingResult(false, false, message);
    }
}
