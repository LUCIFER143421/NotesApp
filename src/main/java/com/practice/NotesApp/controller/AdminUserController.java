package com.practice.NotesApp.controller;

import com.practice.NotesApp.DTO.UpdateUserRolesRequestDto;
import com.practice.NotesApp.DTO.UserSummaryDto;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.UserRepository;
import com.practice.NotesApp.service.UserAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserAdminService userAdminService;
    private final UserRepository userRepository;

    public AdminUserController(UserAdminService userAdminService, UserRepository userRepository) {
        this.userAdminService = userAdminService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    public ResponseEntity<List<UserSummaryDto>> getUsers() {
        List<UserSummaryDto> users = userRepository.findAll()
                .stream()
                .map(user -> new UserSummaryDto(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet())
                ))
                .toList();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    public ResponseEntity<UserSummaryDto> updateUserRoles(@PathVariable Long userId,
                                                @RequestBody UpdateUserRolesRequestDto dto) {
        try {
            User user = userAdminService.updateUserRoles(userId, dto.getRoles());
            UserSummaryDto result = new UserSummaryDto(
                    user.getId(),
                    user.getUsername(),
                    user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet())
            );
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userAdminService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
