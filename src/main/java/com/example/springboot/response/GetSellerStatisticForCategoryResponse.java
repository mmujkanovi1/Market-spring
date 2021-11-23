package com.example.springboot.response;

import java.math.BigDecimal;

public class GetSellerStatisticForCategoryResponse {

  private Long id;

  private String categoryName;

  private BigDecimal pricePerCategory;

  private Integer numberOfAdvertisementsPerCategory;

  public BigDecimal getPricePerCategory() {
    return pricePerCategory;
  }

  public void setPricePerCategory(final BigDecimal pricePerCategory) {
    this.pricePerCategory = pricePerCategory;
  }

  public Integer getNumberOfAdvertisementsPerCategory() {
    return numberOfAdvertisementsPerCategory;
  }

  public void setNumberOfAdvertisementsPerCategory(final Integer numberOfAdvertisementsPerCategory) {
    this.numberOfAdvertisementsPerCategory = numberOfAdvertisementsPerCategory;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(final String categoryName) {
    this.categoryName = categoryName;
  }
}