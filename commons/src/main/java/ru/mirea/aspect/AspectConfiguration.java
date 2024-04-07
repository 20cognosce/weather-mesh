package ru.mirea.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class AspectConfiguration {

    @Bean
    AspectAuth aspectAuth() {
        return new AspectAuth();
    }
}
