package ru.mirea.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mirea.auth.Role;
import ru.mirea.dto.AuthRequestDto;
import ru.mirea.dto.AuthResponseDto;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key = Jwts.SIG.HS512.key().build();

    public AuthResponseDto getAuthFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return AuthResponseDto.builder()
                    .login(null)
                    .role(Role.UNAUTHORIZED)
                    .token(token)
                    .build();
        }

        return AuthResponseDto.builder()
                .login(claims.getSubject())
                .role(Role.valueOf(claims.get("role", String.class)))
                .token(token)
                .build();
    }

    public String generateToken(AuthRequestDto authRequestDto) {
        //TODO:
        Role role = Objects.equals(authRequestDto.getLogin(), "weather") ? Role.SYSTEM : Role.USER;
        return Jwts.builder()
                .subject(authRequestDto.getLogin())
                .claim("role", role)
                .expiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                .issuedAt(new Date())
                .signWith(key)
                .compact();
    }
}
