package com.practice.NotesApp.service;

import com.practice.NotesApp.exception.NoteNotFoundException;
import com.practice.NotesApp.model.Note;
import com.practice.NotesApp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NoteService {
//    HashMap<String, Note> map=new HashMap<>();
    @Autowired
    private NoteRepository noteRepository;

    public Note create(Note note){
        // to generate random and unique id
        String id = UUID.randomUUID().toString();
        note.setId(id);
        return noteRepository.save(note);
    }

    public List<Note> getAllNote(){
        return new ArrayList<>(noteRepository.findAll());
    }

    public Note getNoteById(String id){
       return noteRepository.findById(id)
                .orElseThrow(() ->
                        new NoteNotFoundException("No note found with id: " + id));
    }

    public Boolean deleteNoteById(String id){
        Note existing=noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("No note found with id:" + id));
        noteRepository.delete(existing);
        return true;
    }

    public Note updateNoteById(String id, Note updatedNote){
//        Note note=new Note(id,title,content);
//        map.put(id,note);
//        return note;
// this doesn't check if note exist or not and may create new note instead of updating if doesn't esit
        Note existingNote=noteRepository.findById(id)
                .orElseThrow(()-> new NoteNotFoundException("No note found with id:"+ id));
            if(updatedNote.getContent() !=null) {
                existingNote.setContent(updatedNote.getContent());
            }
            if(updatedNote.getTitle() != null){
                existingNote.setTitle(updatedNote.getTitle());
            }
            return noteRepository.save(existingNote);
        }
}
