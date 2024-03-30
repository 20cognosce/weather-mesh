package ru.mirea.circuit.breaker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.mirea.circuit.breaker.entity.Audit;
import ru.mirea.circuit.breaker.entity.Permission;
import ru.mirea.circuit.breaker.entity.Status;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UtilService {

    @SneakyThrows
    public static Audit generateAuditEvent(Status old_, Status new_, Permission permission, HttpServletRequest request) {
        String requestInfo = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(UtilService.extractRequestInfo(request));

        log.info(String.format("Generating audit event for request: %s", requestInfo));
        return Audit.builder()
                .oldStatus(old_)
                .newStatus(new_)
                .permission(permission)
                .userAgent(request.getHeader("user-agent"))
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Map<String, String> extractRequestInfo(HttpServletRequest request) {
        Map<String, String> requestMap = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            requestMap.put(headerName, headerValue);
        }

        requestMap.put("Method", request.getMethod());
        requestMap.put("Protocol", request.getProtocol());
        requestMap.put("RequestURI", request.getRequestURI());
        requestMap.put("QueryString", request.getQueryString());
        requestMap.put("RemoteAddr", request.getRemoteAddr());
        requestMap.put("RemoteHost", request.getRemoteHost());
        requestMap.put("RemotePort", String.valueOf(request.getRemotePort()));
        requestMap.put("LocalAddr", request.getLocalAddr());
        requestMap.put("LocalName", request.getLocalName());
        requestMap.put("LocalPort", String.valueOf(request.getLocalPort()));
        requestMap.put("ContentType", request.getContentType());
        requestMap.put("ContentLength", String.valueOf(request.getContentLength()));

        return Collections.unmodifiableMap(requestMap);
    }
}
