package com.example.springboot.request;


import javax.validation.constraints.Size;

public class CreateCategoryRequest {

  private static final int MIN_TITLE_SIZE = 3;

  @Size(min = MIN_TITLE_SIZE, message = "Category name should have at least 3 characters")
  private String categoryName;


  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(final String categoryName) {
    this.categoryName = categoryName;
  }

}
