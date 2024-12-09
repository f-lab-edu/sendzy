package com.donggi.sendzy.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration 은 내부적으로 @Component 를 포함하고 있어 컴포넌트 스캔 대상이 됩니다.
 * 이를 통해 스프링 컨테이너에게 해당 클래스가 Bean 구성 클래스임을 알려줍니다.
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean 은 해당 메서드가 반환하는 객체를 스프링 컨테이너에게 Bean 으로 등록하도록 지시합니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/v1/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(session -> session
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
                .maximumSessions(1)
            )
        ;

        return http.build();
    }

}
