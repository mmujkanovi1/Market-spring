package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class GetAdvertisementWithUsdResponse extends GetAdvertisementResponse {

  @JsonProperty("prizelnUsd")
  private BigDecimal prizelnUsd;

  @JsonProperty("categoryId")
  private Long categoryId;

  public BigDecimal getPrizelnUsd() {
    return prizelnUsd;
  }

  public void setPrizelnUsd(final BigDecimal prizelnUsd) {
    this.prizelnUsd = prizelnUsd;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(final Long categoryId) {
    this.categoryId = categoryId;
  }
}
