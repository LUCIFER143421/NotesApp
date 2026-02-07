package com.practice.NotesApp.service;

import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.NoteRepository;
import com.practice.NotesApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAdminService {

    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final RoleService roleService;

    public UserAdminService(UserRepository userRepository, NoteRepository noteRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.roleService = roleService;
    }

    public User updateUserRoles(Long userId, Set<String> roles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setRoles(roleService.resolveRoles(roles));
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        noteRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}
