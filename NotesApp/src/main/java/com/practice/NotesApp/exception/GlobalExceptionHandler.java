package com.practice.NotesApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // it will handle exception related to not found issues only
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<String> handleNotFound(NoteNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e){
        String error=e.getBindingResult()// it will store all the errors occured during the binding of json
                // into java object like in Note here
                .getFieldError()// it will give error occured in only 1 field even if there are multiple error
                .getDefaultMessage();// it will pass on the message we wrote as error
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    // for exception other than that we use this
    // we can create more exception handlers like that it's purely customization
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("something went wrong");
    }
}
