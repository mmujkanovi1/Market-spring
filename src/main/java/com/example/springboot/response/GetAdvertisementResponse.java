package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class GetAdvertisementResponse {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("pricelnEuro")
  private BigDecimal pricelnEuro;


  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public BigDecimal getPricelnEuro() {
    return pricelnEuro;
  }

  public void setPricelnEuro(final BigDecimal priceLnEuro) {
    this.pricelnEuro = priceLnEuro;
  }

}
