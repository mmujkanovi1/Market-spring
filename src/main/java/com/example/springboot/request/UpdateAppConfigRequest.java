package com.example.springboot.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateAppConfigRequest {

  @NotNull(message = "Cannot create advertisements page without number of page")
  private Integer advertisementPageItemsNo;

  @NotEmpty
  private String advertisementPageDefaultSortField;

  @NotNull(message = "Cannot create users page without number of page")
  private Integer userPageItemsNo;

  @NotEmpty
  private String userPageDefaultSortField;

  public Integer getAdvertisementPageItemsNo() {
    return advertisementPageItemsNo;
  }

  public void setAdvertisementPageItemsNo(final Integer advertisementPageItemsNo) {
    this.advertisementPageItemsNo = advertisementPageItemsNo;
  }

  public String getAdvertisementPageDefaultSortField() {
    return advertisementPageDefaultSortField;
  }

  public void setAdvertisementPageDefaultSortField(final String advertisementPageDefaultSortField) {
    this.advertisementPageDefaultSortField = advertisementPageDefaultSortField;
  }

  public Integer getUserPageItemsNo() {
    return userPageItemsNo;
  }

  public void setUserPageItemsNo(final Integer userPageItemsNo) {
    this.userPageItemsNo = userPageItemsNo;
  }

  public String getUserPageDefaultSortField() {
    return userPageDefaultSortField;
  }

  public void setUserPageDefaultSortField(final String userPageDefaultSortField) {
    this.userPageDefaultSortField = userPageDefaultSortField;
  }
}
