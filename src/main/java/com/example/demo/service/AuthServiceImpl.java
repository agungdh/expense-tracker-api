package com.example.demo.service;

import com.example.demo.model.dto.*;
import com.example.demo.model.entity.Tenant;
import com.example.demo.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    private static final String TOKEN_PREFIX = "token:";
    private static final long TOKEN_EXPIRY_HOURS = 24;

    @Override
    public LoginResponse login(LoginRequest request) {
        Tenant tenant = tenantRepository.findBySubdomain(request.subdomain())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), tenant.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = UUID.randomUUID().toString();
        String key = TOKEN_PREFIX + token;
        redisTemplate.opsForValue().set(key, tenant.getId().toString(), TOKEN_EXPIRY_HOURS, TimeUnit.HOURS);

        return new LoginResponse(token, "Bearer");
    }

    @Override
    public String validateToken(String token) {
        String key = TOKEN_PREFIX + token;
        String tenantId = redisTemplate.opsForValue().get(key);
        if (tenantId == null) {
            throw new RuntimeException("Invalid or expired token");
        }
        return tenantId;
    }

    @Override
    public void logout(String token) {
        String key = TOKEN_PREFIX + token;
        redisTemplate.delete(key);
    }
}
