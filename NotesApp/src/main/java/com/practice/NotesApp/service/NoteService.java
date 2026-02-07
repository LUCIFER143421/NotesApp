package com.practice.NotesApp.service;

import com.practice.NotesApp.exception.NoteNotFoundException;
import com.practice.NotesApp.model.Note;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // ------------------- CREATE -------------------
    public Note createNoteForUser(User user, Note note){
        // generate unique ID
        String id = UUID.randomUUID().toString();
        note.setId(id);

        // link note to user
        note.setUser(user);

        return noteRepository.save(note);
    }

    // ------------------- READ -------------------
    // Get all notes belonging to a user
    public List<Note> getAllNotesForUser(User user){
        return noteRepository.findByUser(user);
    }

    // Get a single note by ID for a specific user
    public Note getNoteByIdForUser(String id, User user){
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("No note found with id: " + id));

        // ownership check
        if(!note.getUser().getId().equals(user.getId())){
            throw new NoteNotFoundException("Note not found for this user");
        }

        return note;
    }

    // ------------------- UPDATE -------------------
    public Note updateNoteForUser(String id, Note updatedNote, User user){
        Note existingNote = getNoteByIdForUser(id, user); // ownership checked

        if(updatedNote.getTitle() != null){
            existingNote.setTitle(updatedNote.getTitle());
        }

        if(updatedNote.getContent() != null){
            existingNote.setContent(updatedNote.getContent());
        }

        return noteRepository.save(existingNote);
    }

    public Note updateNoteById(String id, Note updatedNote) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("No note found with id: " + id));

        if (updatedNote.getTitle() != null) {
            existingNote.setTitle(updatedNote.getTitle());
        }

        if (updatedNote.getContent() != null) {
            existingNote.setContent(updatedNote.getContent());
        }

        return noteRepository.save(existingNote);
    }

    // ------------------- DELETE -------------------
    public Boolean deleteNoteForUser(String id, User user){
        Note existingNote = getNoteByIdForUser(id, user); // ownership checked
        noteRepository.delete(existingNote);
        return true;
    }

    public Boolean deleteNoteById(String id) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("No note found with id: " + id));
        noteRepository.delete(existingNote);
        return true;
    }
}
