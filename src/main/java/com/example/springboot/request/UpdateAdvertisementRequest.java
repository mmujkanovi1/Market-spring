package com.example.springboot.request;


public class UpdateAdvertisementRequest extends CreateAdvertisementRequest {

  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }
}
