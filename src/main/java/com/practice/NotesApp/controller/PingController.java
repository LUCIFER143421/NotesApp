package com.practice.NotesApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    // for checking

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }
}
