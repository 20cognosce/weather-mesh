package ru.mirea.auth.jwt;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.mirea.service.UtilService;

import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class HashManager {

    private final byte[] salt = new byte[16];

    public HashManager() {
        new SecureRandom().nextBytes(this.salt);
    }

    @SneakyThrows
    public String hash(byte[] password) {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.update(salt);
        byte[] hash = digest.digest(password);

        return UtilService.byteArrayToBase64(hash);
    }
}
