package com.example.springboot.request;


import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CreateAdvertisementRequest {
  private static final int MIN_TITLE_SIZE = 3;
  private static final int MIN_DESCRIPTION_SIZE = 10;
  private static final int MAX_DESCRIPTION_SIZE = 50;


  @NotEmpty
  @Size(min = MIN_TITLE_SIZE, message = "Title should have at least 3 characters")
  private String title;

  @NotEmpty
  @Size(min = MIN_DESCRIPTION_SIZE, max = MAX_DESCRIPTION_SIZE, message = "Description should have at between 10 and 50 characters")
  private String description;

  @NotNull(message = "Cannot create advertisement without category")
  private Long categoryId;

  @NotNull(message = "Price should not be null")
  private BigDecimal pricelnEuro;


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


  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(final Long categoryId) {
    this.categoryId = categoryId;
  }

  public BigDecimal getPricelnEuro() {
    return pricelnEuro;
  }

  public void setPricelnEuro(final BigDecimal priceLnEuro) {
    this.pricelnEuro = priceLnEuro;
  }


}
