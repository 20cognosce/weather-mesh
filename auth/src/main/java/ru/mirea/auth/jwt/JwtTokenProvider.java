package ru.mirea.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mirea.auth.Role;
import ru.mirea.dto.AuthRequestDto;
import ru.mirea.dto.AuthResponseDto;
import ru.mirea.service.UtilService;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key = Jwts.SIG.HS512.key().build();
    private final byte[] salt = new byte[16];
    private final Map<String, String> accounts;

    public JwtTokenProvider() {
        new SecureRandom().nextBytes(salt);
        this.accounts = Map.of(
                Account.USER.getLogin(), this.hash(UtilService.readPassword()),
                Account.ADMIN.getLogin(), this.hash(UtilService.readPassword()),
                Account.WEATHER.getLogin(), this.hash(UtilService.readPassword()),
                Account.CIRCUIT_BREAKER.getLogin(), this.hash(UtilService.readPassword())
        );
    }

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
        var hash = accounts.get(login);
        var calculatedHash = hash(UtilService.base64ToByteArray(authRequestDto.getPassword()));

        if (!Objects.equals(hash, calculatedHash)) {
            return Jwts.builder()
                    .claim("role", Role.UNAUTHORIZED)
                    .expiration(new Date())
                    .issuedAt(new Date())
                    .signWith(key)
                    .compact();
        }

        Role role = Account.getAccountByLogin(login).getRole();
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

    @SneakyThrows
    private String hash(byte[] password) {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.update(salt);
        byte[] hash = digest.digest(password);

        return UtilService.byteArrayToBase64(hash);
    }
}
