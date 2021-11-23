package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CurrencyConversionResponse {
  @JsonProperty("Kupovni za devize")
  private String currency;



  public String getCurrency() {
    return currency;
  }

  public void setCurrency(final String currency) {
    this.currency = currency;
  }
}
