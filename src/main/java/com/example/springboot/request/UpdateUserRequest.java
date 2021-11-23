package com.example.springboot.request;


public class UpdateUserRequest extends CreateUserRequest {

  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
