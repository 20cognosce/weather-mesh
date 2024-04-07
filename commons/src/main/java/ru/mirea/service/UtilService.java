package ru.mirea.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.mirea.dto.AuthRequestDto;
import ru.mirea.dto.AuthResponseDto;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class UtilService {

    public static String getTokenFromAuthServiceLogin(String authServiceHost, String requestFrom, String login, byte[] password) {
        return RestClient.create().post()
                .uri(authServiceHost + "/auth/token")
                .header("request-from", requestFrom)
                .header("request-to", "auth")
                .body(new AuthRequestDto(login, password))
                .retrieve()
                .body(String.class);
    }

    public static AuthResponseDto getAccountFromAuthServiceByToken(String authServiceHost, String requestFrom, String authToken) {
        return RestClient.create().get()
                .uri(authServiceHost + "/auth/account")
                .header(HttpHeaders.AUTHORIZATION, authToken)
                .header("request-from", requestFrom)
                .header("request-to", "auth")
                .retrieve()
                .body(AuthResponseDto.class);
    }

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

        return toJson(requestMap);
    }

    @SneakyThrows
    public static String toJson(Object object) {
        return new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    public static void logRequest(String applicationName) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        log.info(String.format(
                "%s service received new request: \n%s",
                applicationName,
                UtilService.extractRequestInfo(request)));
    }

    @SneakyThrows
    public static byte[] readPassword() {
        Path path = new ClassPathResource("password").getFile().toPath();
        return Files.readAllBytes(path);
    }
}
