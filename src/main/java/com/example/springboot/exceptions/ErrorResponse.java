package com.example.springboot.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ErrorResponse {

  private HttpStatus status;
  private List<String> message;

  public ErrorResponse(final HttpStatus status, final List<String> message) {
    super();
    this.status = status;
    this.message = message;
  }

  public ErrorResponse(final HttpStatus status, final String error) {
    super();
    this.status = status;
    message = Arrays.asList(error);
  }

  public HttpStatus getStatus() {
    return status;
  }


  public List<String> getErrors() {
    return message;
  }
}
