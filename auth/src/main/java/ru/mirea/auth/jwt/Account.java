package ru.mirea.auth.jwt;

import lombok.Getter;
import ru.mirea.auth.Role;

@Getter
public enum Account {
    USER("user", Role.USER),
    ADMIN("admin", Role.ADMIN),
    WEATHER("weather", Role.SYSTEM),
    CIRCUIT_BREAKER("circuit-breaker", Role.SYSTEM),
    UNAUTHORIZED(null, Role.UNAUTHORIZED);

    private final String login;
    private final Role role;

    Account(String login, Role role) {
        this.login = login;
        this.role = role;
    }

    public static Account getAccountByLogin(String login) {
        switch (login) {
            case "user" -> {
                return USER;
            }
            case "admin" -> {
                return ADMIN;
            }
            case "weather" -> {
                return WEATHER;
            }
            case "circuit-breaker" -> {
                return CIRCUIT_BREAKER;
            }
            default -> throw new IllegalArgumentException(
                    String.format("Account login '%s' is not registered in authentication service", login)
            );
        }
    }
}
