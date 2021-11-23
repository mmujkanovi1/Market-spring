package com.example.springboot.response;

import com.example.springboot.request.CreateUserRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserResponse extends CreateUserRequest {

  @JsonProperty("id")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
