package com.practice.NotesApp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Note {
    @Id
//    @GeneratedValue
    private String id;
    private String title;
    private String content;
}
