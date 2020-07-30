package com.example.JWTAuthenticationService.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProviderUtil jwtTokenProviderUtil;

    public JwtTokenFilter(JwtTokenProviderUtil jwtTokenProviderUtil) {
        this.jwtTokenProviderUtil = jwtTokenProviderUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        String token = jwtTokenProviderUtil.resolveToken((HttpServletRequest) servletRequest);

        if (token != null && jwtTokenProviderUtil.validateToken(token)) {
            Authentication authentication = jwtTokenProviderUtil.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
