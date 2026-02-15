package com.practice.NotesApp.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDto {
    @NotBlank(message = "title can't be empty")
    private String title;
    @NotBlank(message="content can't be empty")
    private String content;
}
