package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class CategoryStatisticsResponse {

  @JsonProperty("CategoriesForUser")
  private List<GetSellerStatisticForCategoryResponse> getSellerStatisticForCategories;

  private BigDecimal totalPrize;

  private Integer totalAdvertisement;

  public List<GetSellerStatisticForCategoryResponse> getGetSellerStatisticForCategories() {
    return getSellerStatisticForCategories;
  }

  public void setGetSellerStatisticForCategories(final List<GetSellerStatisticForCategoryResponse> getSellerStatisticForCategories) {
    this.getSellerStatisticForCategories = getSellerStatisticForCategories;
  }

  public BigDecimal getTotalPrize() {
    return totalPrize;
  }


  public void setTotalPrize(final BigDecimal totalPrize) {
    this.totalPrize = totalPrize;
  }

  public Integer getTotalAdvertisement() {
    return totalAdvertisement;
  }

  public void setTotalAdvertisement(final Integer totalAdvertisement) {
    this.totalAdvertisement = totalAdvertisement;
  }
}
