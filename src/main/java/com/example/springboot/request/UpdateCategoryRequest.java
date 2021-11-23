package com.example.springboot.request;

public class UpdateCategoryRequest extends CreateCategoryRequest {

  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
