package com.practice.NotesApp.repository;

import com.practice.NotesApp.model.Role;
import com.practice.NotesApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);
    boolean existsByUsername(String username);
    boolean existsByRoles_Name(String name);
}
