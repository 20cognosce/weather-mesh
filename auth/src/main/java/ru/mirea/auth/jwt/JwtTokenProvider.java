package ru.mirea.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mirea.auth.Role;
import ru.mirea.dto.AuthRequestDto;
import ru.mirea.dto.AuthResponseDto;
import ru.mirea.service.UtilService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component
public class JwtTokenProvider {
    private final AccountRegistry accountRegistry;
    private final HashManager hashManager;
    private final SecretKey key = Jwts.SIG.HS512.key().build();

    public AuthResponseDto getAuthFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return AuthResponseDto.builder()
                    .login(claims.getSubject())
                    .role(Role.valueOf(claims.get("role", String.class)))
                    .token(token)
                    .build();
        } catch (Exception e) {
            log.info("Invalid token provided: " + e.getMessage());
            return AuthResponseDto.builder()
                    .role(Role.UNAUTHORIZED)
                    .token(token)
                    .build();
        }
    }

    public String generateToken(AuthRequestDto authRequestDto) {
        var login = authRequestDto.getLogin();
        var hash = accountRegistry.getAccountByLogin(login).hash();
        var calculatedHash = hashManager.hash(UtilService.base64ToByteArray(authRequestDto.getPassword()));

        if (!Objects.equals(hash, calculatedHash)) {
            return Jwts.builder()
                    .claim("role", Role.UNAUTHORIZED)
                    .expiration(new Date())
                    .issuedAt(new Date())
                    .signWith(key)
                    .compact();
        }

        Role role = accountRegistry.getAccountByLogin(login).role();
        return Jwts.builder()
                .subject(authRequestDto.getLogin())
                .claim("role", role)
                .expiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .issuedAt(new Date())
                .signWith(key)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
