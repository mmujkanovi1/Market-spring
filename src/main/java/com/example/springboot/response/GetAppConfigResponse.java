package com.example.springboot.response;


public class GetAppConfigResponse {


  private Integer advertisementPageItemsNo;

  private String advertisementPageDefaultSortField;

  private Integer userPageItemsNo;

  private String userPageDefaultSortField;

  private Long userId;

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(final Long userId) {
    this.userId = userId;
  }
}
