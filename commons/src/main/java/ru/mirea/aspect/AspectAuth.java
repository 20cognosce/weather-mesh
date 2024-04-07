package ru.mirea.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.mirea.auth.AuthRole;
import ru.mirea.dto.AuthResponseDto;
import ru.mirea.service.UtilService;

import java.lang.reflect.Method;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Aspect
public class AspectAuth {

    @Value("${spring.application.name}")
    private String requestFrom;

    @Value("${hostname.auth}")
    private String authServiceHost;

    @Around("@annotation(ru.mirea.auth.AuthRole)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        log.info("Aspect triggered on method: " + method.getName());

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info(String.format("Retrieved '%s' header: %s", HttpHeaders.AUTHORIZATION, authToken));

        AuthResponseDto responseDto = UtilService.getAccountFromAuthServiceByToken(authServiceHost, requestFrom, authToken);
        log.info("Received response from auth service: \n" + UtilService.toJson(responseDto));

        AuthRole annotation = method.getAnnotation(AuthRole.class);
        if (responseDto.getRole() != annotation.value()) {
            return ResponseEntity
                    .of(ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Invalid authorization token or insufficient role privileges"))
                    .build();
        }

        log.info(String.format("Response role %s corresponds with annotation role %s successfully", responseDto.getRole(), annotation.value()));
        return joinPoint.proceed();
    }
}