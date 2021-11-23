package com.example.springboot.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCategoryResponse extends CreateCategoryResponse {

  @JsonProperty
  private String categoryName;



  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(final String categoryName) {
    this.categoryName = categoryName;
  }
}
