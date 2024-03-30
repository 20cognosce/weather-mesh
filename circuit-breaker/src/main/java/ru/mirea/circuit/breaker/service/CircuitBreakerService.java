package ru.mirea.circuit.breaker.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.circuit.breaker.dto.PermissionDto;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.Status;
import ru.mirea.circuit.breaker.entity.System;
import ru.mirea.circuit.breaker.repo.AuditRepository;
import ru.mirea.circuit.breaker.repo.PermissionRepository;
import ru.mirea.circuit.breaker.repo.StatusRepository;
import ru.mirea.circuit.breaker.repo.SystemRepository;

@Slf4j
@Service
public class CircuitBreakerService {

    private final AuditRepository auditRepository;
    private final StatusRepository statusRepository;
    private final SystemRepository systemRepository;
    private final PermissionRepository permissionRepository;

    public CircuitBreakerService(AuditRepository auditRepository,
                                 StatusRepository statusRepository, SystemRepository systemRepository,
                                 PermissionRepository permissionRepository) {
        this.auditRepository = auditRepository;
        this.statusRepository = statusRepository;
        this.systemRepository = systemRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public Permission updatePermission(PermissionDto permissionDto, HttpServletRequest request) {
        Permission permissionForUpdate = permissionRepository.findById(permissionDto.getId()).orElseThrow(IllegalArgumentException::new);
        System fromSystemForUpdate = systemRepository.findByName(permissionDto.getRequestFromSystem()).orElseThrow(IllegalArgumentException::new);
        System toSystemForUpdate = systemRepository.findByName(permissionDto.getRequestToSystem()).orElseThrow(IllegalArgumentException::new);
        Status statusForUpdate = statusRepository.findByValue(permissionDto.getStatus()).orElseThrow(IllegalArgumentException::new);

        Audit audit = UtilService.generateAuditEvent(permissionForUpdate.getStatus(), statusForUpdate, permissionForUpdate, request);

        permissionForUpdate.setRequestFromSystem(fromSystemForUpdate);
        permissionForUpdate.setRequestToSystem(toSystemForUpdate);
        permissionForUpdate.setStatus(statusForUpdate);

        var saved = permissionRepository.save(permissionForUpdate);
        auditRepository.save(audit);
        return saved;
    }


}
