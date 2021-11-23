package com.example.springboot.response;

import java.io.Serializable;

public class JwtResponse implements Serializable {

  private static final long serialVersionUID = -8091879091924046844L;
  private final String jwttoken;

  private Long id;

  public JwtResponse(final String jwttoken, final Long id) {
    this.jwttoken = jwttoken;
    this.id=id;
  }

  public String getToken() {
    return this.jwttoken;
  }

  public Long getId() {
    return id;
  }
}