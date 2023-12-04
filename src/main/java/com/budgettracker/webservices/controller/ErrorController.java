package com.budgettracker.webservices.controller;

import com.budgettracker.webservices.model.WebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> exception(ResponseStatusException exception) {
        WebResponse<String> result = new WebResponse<>();
        result.setError(exception.getReason());
        return ResponseEntity.status(exception.getStatusCode()).body(result);
    }
}
