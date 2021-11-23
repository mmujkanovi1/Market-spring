package com.example.springboot.response;

public class ImageReponse {
  private Long id;
  private String name;
  private Long size;
  private String url;
  private String contentType;
  private Long advertisementId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Long getAdvertisementId() {
    return advertisementId;
  }

  public void setAdvertisementId(Long advertisementId) {
    this.advertisementId = advertisementId;
  }
}
