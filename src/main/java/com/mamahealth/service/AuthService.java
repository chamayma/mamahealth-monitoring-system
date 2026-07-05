package com.mamahealth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.auth.AuthResponse;
import com.mamahealth.dto.auth.LoginRequest;
import com.mamahealth.dto.auth.SignupRequest;
import com.mamahealth.entity.Role;
import com.mamahealth.entity.User;
import com.mamahealth.repository.UserRepository;
import com.mamahealth.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String signup(SignupRequest request) {

        User existingUser = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (existingUser != null) {
            return "Email already exists";
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MOTHER);

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getRole().name()
        );

        return new AuthResponse(
                token,
                user.getRole().name()
        );
    }
}