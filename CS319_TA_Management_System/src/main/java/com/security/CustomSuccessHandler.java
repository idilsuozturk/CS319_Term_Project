package com.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/frontend/admin.html");
        } else if (roles.contains("ROLE_INSTRUCTOR")) {
            response.sendRedirect("/frontend/instructor.html");
        } else if (roles.contains("ROLE_STUDENT")) {
            response.sendRedirect("/frontend/student.html");
        } else {
            response.sendRedirect("/frontend/index.html"); // default fallback
        }
    }
}