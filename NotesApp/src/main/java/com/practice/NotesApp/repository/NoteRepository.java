package com.practice.NotesApp.repository;

import com.practice.NotesApp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,String> {
}
