package com.practice.NotesApp.controller;

import com.practice.NotesApp.DTO.NoteRequestDto;
import com.practice.NotesApp.DTO.NoteResponseDto;
import com.practice.NotesApp.mapper.NoteMapper;
import com.practice.NotesApp.model.Note;
import com.practice.NotesApp.model.User;
import com.practice.NotesApp.repository.UserRepository;
import com.practice.NotesApp.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserRepository userRepository;

    // ------------------- GET ALL NOTES FOR USER -------------------
    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getAll(Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Note> notes = noteService.getAllNotesForUser(user);
        return ResponseEntity.ok(NoteMapper.noteResponseDtoList(notes));
    }

    // ------------------- GET NOTE BY ID FOR USER -------------------
    @GetMapping("/id/{myId}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable String myId, Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = noteService.getNoteByIdForUser(myId, user);
        return ResponseEntity.ok(NoteMapper.noteResponseDto(note));
    }

    // ------------------- CREATE NOTE -------------------
    @PostMapping
    public ResponseEntity<NoteResponseDto> post(@Valid @RequestBody NoteRequestDto dto, Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = NoteMapper.toEntity(dto);
        Note saved = noteService.createNoteForUser(user, note);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(NoteMapper.noteResponseDto(saved));
    }

    // ------------------- UPDATE NOTE -------------------
    @PutMapping("/id/{myId}")
    public ResponseEntity<NoteResponseDto> put(@Valid @RequestBody NoteRequestDto dto,
                                               @PathVariable String myId,
                                               Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = NoteMapper.toEntity(dto);
        Note updated = noteService.updateNoteForUser(myId, note, user);
        return ResponseEntity.ok(NoteMapper.noteResponseDto(updated));
    }

    // ------------------- UPDATE ANY NOTE (EDITOR) -------------------
    @PutMapping("/any/id/{myId}")
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity<NoteResponseDto> putAny(@Valid @RequestBody NoteRequestDto dto,
                                                  @PathVariable String myId){
        Note note = NoteMapper.toEntity(dto);
        Note updated = noteService.updateNoteById(myId, note);
        return ResponseEntity.ok(NoteMapper.noteResponseDto(updated));
    }

    // ------------------- DELETE NOTE -------------------
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteById(@PathVariable String myId, Principal principal){
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        noteService.deleteNoteForUser(myId, user);
        return ResponseEntity.ok("Note deleted successfully!");
    }

    // ------------------- DELETE ANY NOTE (ADMIN) -------------------
    @DeleteMapping("/any/id/{myId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAny(@PathVariable String myId){
        noteService.deleteNoteById(myId);
        return ResponseEntity.ok("Note deleted successfully!");
    }
}
