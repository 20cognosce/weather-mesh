package ru.mirea.circuit.breaker.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.Status;
import ru.mirea.service.UtilService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class AuditService {

    public Audit generateAuditEvent(Status old_, Status new_, Permission permission, String username, HttpServletRequest request) {
        String requestInfo = UtilService.extractRequestInfo(request);

        log.info(String.format("Generating audit event for request: %s", requestInfo));
        return Audit.builder()
                .oldStatus(old_)
                .newStatus(new_)
                .permission(permission)
                .username(username)
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }
}
