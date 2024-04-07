package ru.mirea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.auth.Role;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    String login;
    Role role;
    String token;
}
