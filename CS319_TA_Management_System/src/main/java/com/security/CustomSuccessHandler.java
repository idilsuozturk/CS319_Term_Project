package com.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_INSTRUCTOR")) {
            response.sendRedirect("/frontend/instructor/instructor_main.html");
        } else if (roles.contains("ROLE_TA")) {
            response.sendRedirect("/frontend/ta/main.html");
        }else if (roles.contains("ROLE_DEPARTMENT_STAFF")) {
            response.sendRedirect("/frontend/staff/staff_main.html");
        } else if (roles.contains("ROLE_DEPARTMENT_CHAIR")) {
            response.sendRedirect("/frontend/department_chair/department_chair_main.html");
        } else {
            response.sendRedirect("/frontend/index.html"); // default fallback
        }
    }
}