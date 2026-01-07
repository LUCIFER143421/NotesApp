package com.practice.NotesApp.controller;

import com.practice.NotesApp.DTO.LoginRequestDto;
import com.practice.NotesApp.DTO.LoginResponseDto;
import com.practice.NotesApp.DTO.RegisterRequestDto;
import com.practice.NotesApp.model.Role;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.RoleRepository;
import com.practice.NotesApp.repository.UserRepository;
import com.practice.NotesApp.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return "Username already exists!";
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // assign default role
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        String token = jwtUtil.generateToken(loginRequestDto.getUsername());
        return new LoginResponseDto(token);
    }
}
