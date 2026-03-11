package com.example.demo.service;

import com.example.demo.model.dto.*;
import java.util.List;
import java.util.UUID;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    String validateToken(String token);
    void logout(String token);
}
