package com.mamahealth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.auth.SignupRequest;
import com.mamahealth.entity.Role;
import com.mamahealth.entity.User;
import com.mamahealth.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signup(SignupRequest request) {

        User existingUser = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (existingUser != null) {
            return "Email already exists";
        }

        User user = new User();

        user.setEmail(request.getEmail());

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.MOTHER);

        userRepository.save(user);

        return "User registered successfully";
    }
}