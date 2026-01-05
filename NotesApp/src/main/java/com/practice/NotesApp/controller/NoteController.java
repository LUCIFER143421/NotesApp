package com.practice.NotesApp.controller;

import com.practice.NotesApp.DTO.NoteRequestDto;
import com.practice.NotesApp.DTO.NoteResponseDto;
import com.practice.NotesApp.mapper.NoteMapper;
import com.practice.NotesApp.model.Note;
import com.practice.NotesApp.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.practice.NotesApp.mapper.NoteMapper.*;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

// no need to autowire NoteRequestDto

    @GetMapping()
    public ResponseEntity<List<NoteResponseDto>> getAll(){
       List<Note> notes=noteService.getAllNote();
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(noteResponseDtoList(notes));
       // now instead of creating list pf NoteResponseDto here we will clean it using NoteMapper
//       return ResponseEntity
//               .status(HttpStatus.OK)
//               .body(notes.stream()
//                       .map(note ->new NoteResponseDto(note.getId(),
//                               note.getTitle(),
//                               note.getContent()))
//                       .toList());

//        List<NoteResponseDto> result = new ArrayList<>();
//
//        for (Note note : notes) {
//            result.add(
//                    new NoteResponseDto(
//                            note.getId(),
//                            note.getTitle(),
//                            note.getContent()
//                    )
//            );
//        }
//
//        return result;
        // without lambda expression (lambda expression is just shortcut of writing
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<NoteResponseDto> getById(@PathVariable String myId){
        Note note=noteService.getNoteById(myId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteResponseDto(note));
        // here also we will use noteMapper
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new NoteResponseDto(
//                        note.getId(),
//                        note.getTitle(),
//                        note.getContent()
//                ));
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> post(@Valid @RequestBody NoteRequestDto dto){
        Note note=toEntity(dto);
        Note saved=noteService.create(note);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NoteMapper.noteResponseDto(saved));
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(new NoteResponseDto(
//                        note.getId(),
//                        note.getTitle(),
//                        note.getContent()
//                ));
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<NoteResponseDto> put(@Valid @RequestBody NoteRequestDto dto, @PathVariable String myId){
        Note note=toEntity(dto);
        Note updated=noteService.updateNoteById(myId,note);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(NoteMapper.noteResponseDto(updated));
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new NoteResponseDto(
//                        note.getId(),
//                        note.getTitle(),
//                        note.getContent()
//                ));
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String myId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(noteService.deleteNoteById(myId));
    }
}
