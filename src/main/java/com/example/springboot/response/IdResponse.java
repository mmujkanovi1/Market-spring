package com.example.springboot.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class IdResponse {

  @JsonProperty("id")
  private Long id;


  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
