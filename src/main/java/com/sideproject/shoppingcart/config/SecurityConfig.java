package com.sideproject.shoppingcart.config;

import com.sideproject.shoppingcart.security.CustomAccessDeniedHandler;
import com.sideproject.shoppingcart.security.UserRoleFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                // 🔹 允許 Swagger 相關資源存取，不需要登入
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs","/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                        // 🔹 允許未登入使用的 API
                        .requestMatchers("/auth/login", "/auth/register", "/products/**", "/product/categories/").permitAll()
                        // 🔹 限制管理 API
                        .requestMatchers("/products/maintenance/**").hasAuthority("ROLE_ADMIN")
                        // 🔹 其他 API 預設需要登入
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new UserRoleFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

}

