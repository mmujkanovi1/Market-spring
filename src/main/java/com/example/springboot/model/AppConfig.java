package com.example.springboot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "appConfig")
@Table(name = "appConfig")
public class AppConfig {

  private static final int NUMBER_FOR_PAGES = 5;

  @Id
  private int id = 1;

  @Column(
          name = "advertisementPageItemsNo"
  )
  private Integer advertisementPageItemsNo = NUMBER_FOR_PAGES;

  @Column(
          name = "advertisementPageDefaultSortField"
  )
  private String advertisementPageDefaultSortField = "id";

  @Column(
          name = "userPageItemsNo"
  )
  private Integer userPageItemsNo = NUMBER_FOR_PAGES;

  @Column(
          name = "userPageDefaultSortField"
  )
  private String userPageDefaultSortField = "id";

  @Column(
          name = "user_id"
  )
  private Long userId;

  @Column(name = "createdAt")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updatedAt")
  @UpdateTimestamp
  private LocalDateTime updatedAt;


  public String getAdvertisementPageDefaultSortField() {
    return advertisementPageDefaultSortField;
  }

  public void setAdvertisementPageDefaultSortField(final String advertisementPageDefaultSortField) {
    this.advertisementPageDefaultSortField = advertisementPageDefaultSortField;
  }

  public String getUserPageDefaultSortField() {
    return userPageDefaultSortField;
  }

  public void setUserPageDefaultSortField(final String userPageDefaultSortField) {
    this.userPageDefaultSortField = userPageDefaultSortField;
  }

  public Integer getAdvertisementPageItemsNo() {
    return advertisementPageItemsNo;
  }

  public void setAdvertisementPageItemsNo(final Integer advertisementPageItemsNo) {
    this.advertisementPageItemsNo = advertisementPageItemsNo;
  }

  public Integer getUserPageItemsNo() {
    return userPageItemsNo;
  }

  public void setUserPageItemsNo(final Integer userPageItemsNo) {
    this.userPageItemsNo = userPageItemsNo;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(final Long userId) {
    this.userId = userId;
  }
}
