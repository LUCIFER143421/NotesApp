package com.practice.NotesApp.repository;

import com.practice.NotesApp.model.Note;
import com.practice.NotesApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, String> {

    List<Note> findByUser(User user);

    Optional<Note> findByIdAndUser(String id, User user);
}
