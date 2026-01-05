package com.practice.NotesApp.mapper;

import com.practice.NotesApp.DTO.NoteRequestDto;
import com.practice.NotesApp.DTO.NoteResponseDto;
import com.practice.NotesApp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteMapper {
        public static NoteResponseDto noteResponseDto(Note note){
            if(note == null){
                return null;
            }
            return new NoteResponseDto(
                    note.getId(),
                    note.getTitle(),
                    note.getContent()
            );
        }

        public static List<NoteResponseDto> noteResponseDtoList(List<Note> notes){
            List<NoteResponseDto> result=new ArrayList<>();
            for(Note note:notes){
                result.add(noteResponseDto(note));
            }
            return result;
        }

    public static Note toEntity(NoteRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        return note;
    }
}
