package ru.mirea.auth.jwt;

import org.springframework.stereotype.Component;
import ru.mirea.auth.Role;
import ru.mirea.service.UtilService;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountRegistry {
    private final Map<String, Account> registry;

    public AccountRegistry(HashManager hashManager) {
        var user = new Account("default_user", Role.USER, hashManager.hash(UtilService.readPassword()));
        var admin = new Account("default_admin", Role.ADMIN, hashManager.hash(UtilService.readPassword()));
        var weather = new Account("weather", Role.SYSTEM, hashManager.hash(UtilService.readPassword()));
        var circuitBreaker = new Account("circuit-breaker", Role.SYSTEM, hashManager.hash(UtilService.readPassword()));
        var unauthorized = new Account(null, Role.UNAUTHORIZED, null);

        this.registry = new HashMap<>() {{
            put(user.login, user);
            put(admin.login, admin);
            put(weather.login, weather);
            put(circuitBreaker.login, circuitBreaker);
            put(unauthorized.login, unauthorized);
        }};

    }

    public Account getAccountByLogin(String login) {
        var account = this.registry.get(login);

        if (account == null) {
            throw new IllegalArgumentException(
                    String.format("Account login '%s' is not registered in authentication service", login)
            );
        }

        return account;
    }

    public record Account(String login, Role role, String hash) {
    }
}
