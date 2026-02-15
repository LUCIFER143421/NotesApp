package com.practice.NotesApp.controller;

import com.practice.NotesApp.DTO.UpdateUserRolesRequestDto;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.service.UserAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserAdminService userAdminService;

    public AdminUserController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    public ResponseEntity<User> updateUserRoles(@PathVariable Long userId,
                                                @RequestBody UpdateUserRolesRequestDto dto) {
        try {
            return ResponseEntity.ok(userAdminService.updateUserRoles(userId, dto.getRoles()));
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
