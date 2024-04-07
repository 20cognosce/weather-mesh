package ru.mirea.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.auth.jwt.JwtTokenProvider;
import ru.mirea.dto.AuthRequestDto;
import ru.mirea.dto.AuthResponseDto;
import ru.mirea.service.UtilService;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Value("${spring.application.name}")
    private String applicationName;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/token")
    public ResponseEntity<String> createToken(@RequestBody AuthRequestDto requestDto) {
        //TODO: validate login and password
        UtilService.logRequest(applicationName);
        String token = jwtTokenProvider.generateToken(requestDto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/account")
    public ResponseEntity<AuthResponseDto> getUserByToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {
        UtilService.logRequest(applicationName);
        AuthResponseDto auth = jwtTokenProvider.getAuthFromToken(token);
        return ResponseEntity.ok(auth);
    }
}
