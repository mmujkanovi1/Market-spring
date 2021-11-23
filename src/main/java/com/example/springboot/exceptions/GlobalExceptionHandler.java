package com.example.springboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value
          = {MethodArgumentNotValidException.class})
  protected ResponseEntity<Object> handleInputErrors(
          final MethodArgumentNotValidException ex, final WebRequest request) {

    List<String> message = new ArrayList<String>();
    for (int i = 0; i < ex.getAllErrors().size(); i++) {
      message.add(ex.getAllErrors().get(i).getDefaultMessage());
    }
    ErrorResponse errorResponse =
            new ErrorResponse(HttpStatus.BAD_REQUEST, message);
    return new ResponseEntity<Object>(
            errorResponse, errorResponse.getStatus());
  }

  @ExceptionHandler(value
          = {NoSuchElementException.class})
  protected ResponseEntity<Object> handleNoSuchElementExceptions(
          final NoSuchElementException ex, final WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());


    return new ResponseEntity<Object>(
            errorResponse,
            errorResponse.getStatus()
    );
  }

  @ExceptionHandler(value
          = {IOException.class})
  protected ResponseEntity<Object> handleIOException(
          final IOException ex, final WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    return new ResponseEntity<Object>(
            errorResponse,
            errorResponse.getStatus()
    );
  }
}
