package com.ronakcode.onlinelibrary.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AccessDeniedExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain fc) throws ServletException, IOException {
        try {
            fc.doFilter(req, res);
        } catch (AccessDeniedException e) {
            // log error if needed here then redirect
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("");
            requestDispatcher.forward(req, res);

        }
    }
}