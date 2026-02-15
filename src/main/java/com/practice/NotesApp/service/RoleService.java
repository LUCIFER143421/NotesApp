package com.practice.NotesApp.service;

import com.practice.NotesApp.model.Role;
import com.practice.NotesApp.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private static final Set<String> ALLOWED_ROLES = Set.of(
            "ROLE_ADMIN",
            "ROLE_USER",
            "ROLE_EDITOR",
            "ROLE_DEVELOPER"
    );

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> resolveDefaultUserRole() {
        return resolveRoles(Set.of("ROLE_USER"));
    }

    public Set<Role> resolveRoles(Set<String> requestedRoles) {
        Set<String> normalizedRoles = normalizeRequestedRoles(requestedRoles);
        if (normalizedRoles.isEmpty()) {
            throw new IllegalArgumentException("At least one valid role is required.");
        }

        Set<Role> roles = new HashSet<>();
        for (String roleName : normalizedRoles) {
            Role role = roleRepository.findByName(roleName).orElse(null);
            if (role == null) {
                role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
            roles.add(role);
        }
        return roles;
    }

    private Set<String> normalizeRequestedRoles(Set<String> requestedRoles) {
        if (requestedRoles == null || requestedRoles.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> normalized = requestedRoles.stream()
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .map(String::toUpperCase)
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .filter(ALLOWED_ROLES::contains)
                .collect(Collectors.toSet());

        return new HashSet<>(normalized);
    }
}
