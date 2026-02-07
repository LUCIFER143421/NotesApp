package com.practice.NotesApp.controller;

import com.practice.NotesApp.DTO.LoginRequestDto;
import com.practice.NotesApp.DTO.LoginResponseDto;
import com.practice.NotesApp.DTO.RegisterRequestDto;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.UserRepository;
import com.practice.NotesApp.config.JwtUtil;
import com.practice.NotesApp.service.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            return "Username already exists!";
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(roleService.resolveDefaultUserRole());

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
