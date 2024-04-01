package ru.mirea.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class UtilService {
    @SneakyThrows
    public static String extractRequestInfo(HttpServletRequest request) {
        Map<String, String> requestMap = new HashMap<>();

        requestMap.put("Method", request.getMethod());
        requestMap.put("Protocol", request.getProtocol());
        requestMap.put("RequestURI", request.getRequestURI());
        requestMap.put("RemoteAddr", request.getRemoteAddr());
        requestMap.put("RemoteHost", request.getRemoteHost());
        requestMap.put("LocalAddr", request.getLocalAddr());
        requestMap.put("LocalName", request.getLocalName());
        requestMap.put("Cookies", Arrays.toString(request.getCookies()));

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            requestMap.put(headerName, headerValue);
        }

        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(requestMap);
    }
}
