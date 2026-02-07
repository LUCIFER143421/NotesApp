package com.practice.NotesApp.config;

import com.practice.NotesApp.model.Role;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.UserRepository;
import com.practice.NotesApp.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.username:admin}")
    private String adminUsername;

    @Value("${app.bootstrap.admin.password:admin123}")
    private String adminPassword;

    public AdminSeeder(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByRoles_Name("ROLE_ADMIN")) {
            return;
        }

        User user = userRepository.findByUsername(adminUsername).orElse(null);
        if (user == null) {
            user = new User();
            user.setUsername(adminUsername);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRoles(roleService.resolveRoles(Set.of("ROLE_ADMIN")));
            userRepository.save(user);
            return;
        }

        Set<Role> roles = new HashSet<>(user.getRoles());
        roles.addAll(roleService.resolveRoles(Set.of("ROLE_ADMIN")));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
