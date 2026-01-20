package com.sideproject.shoppingcart.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserRoleFilter implements Filter {

    // 角色名稱對應權限
    private static final Map<String, String> ROLE_PERMISSIONS = Map.of(
            "ADMIN", "ROLE_ADMIN",
            "USER", "ROLE_USER",
            "GUEST","ROLE_GUEST"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getServletPath();

        // 如果是登入、註冊或是 Swagger，直接跳過這個 Filter
        if (path.startsWith("/auth/") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        // 讀取 Header 中的角色名稱
        String roleHeader = httpRequest.getHeader("X-USER-ROLE");

        if (roleHeader == null) {
            roleHeader = "GUEST"; //如果都不是，設定為訪客
        }

        // 查詢角色對應的權限
        String rolePermission = ROLE_PERMISSIONS.get(roleHeader.toUpperCase());

        if (rolePermission == null) {
            // 如果角色無效，回應 403
            sendForbiddenResponse(httpResponse, "帳號權限有誤，請聯絡管理員", httpRequest);
            return;
        }

        // 設定 SecurityContext
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("user", null,
                        Collections.singletonList(new SimpleGrantedAuthority(rolePermission)));

        SecurityContextHolder.getContext().setAuthentication(auth);

        //當 @PreAuthorize 阻擋時，返回 JSON 錯誤訊息 而不是讓 Spring Security 直接拋出 403
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            sendForbiddenResponse(httpResponse, "你沒有權限執行此操作，請聯絡管理員", httpRequest);
        }
    }

    private void sendForbiddenResponse(HttpServletResponse response, String message, HttpServletRequest request) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", 403);
        errorDetails.put("error", "Forbidden");
        errorDetails.put("message", message);
        errorDetails.put("path", request.getRequestURI());

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
    }
}
