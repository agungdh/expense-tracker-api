package com.example.demo.security;

import com.example.demo.repository.TenantRepository;
import com.example.demo.service.AuthService;
import com.example.demo.util.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final TenantRepository tenantRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String subdomain = extractSubdomain(request);
        
        if (subdomain != null) {
            TenantContext.setTenantId(null);
            
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                try {
                    String token = authHeader.substring(7);
                    String tenantIdStr = authService.validateToken(token);
                    UUID tenantId = UUID.fromString(tenantIdStr);
                    
                    tenantRepository.findById(tenantId).ifPresent(tenant -> {
                        TenantContext.setTenantId(tenant.getId());
                        
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(tenant, null, Collections.emptyList());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                } catch (Exception e) {
                }
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String extractSubdomain(HttpServletRequest request) {
        String host = request.getHeader("Host");
        if (host == null) {
            return null;
        }
        
        String[] parts = host.split(":")[0].split("\\.");
        if (parts.length >= 3) {
            return parts[0];
        }
        
        return null;
    }
}
