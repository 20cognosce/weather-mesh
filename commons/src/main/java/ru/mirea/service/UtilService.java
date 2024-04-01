package ru.mirea.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class UtilService {
    @SneakyThrows
    public static String extractRequestInfo(HttpServletRequest request) {
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

        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestMap);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipProvidedByProxy = request.getHeader("X-FORWARDED-FOR");
        return ipProvidedByProxy == null ? request.getRemoteAddr() : ipProvidedByProxy;
    }
}
