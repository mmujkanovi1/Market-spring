package com.example.springboot.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Advertisements")
@Table(name = "advertisements")
public class Advertisement {

  private static final int MIN_TITLE_SIZE = 3;
  private static final int MIN_DESCRIPTION_SIZE = 10;
  private static final int MAX_DESCRIPTION_SIZE = 50;


  @Id
  @SequenceGenerator(
          name = "advertisement_sequence",
          sequenceName = "advertisement_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "advertisement_sequence"
  )
  @Column(
          name = "id"
  )
  private Long id;


  @Column(
          name = "title"
  )
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "advertisement")
  private List<Images> images = new ArrayList<Images>();

  @Column(name = "pricelnEuro")
  private BigDecimal pricelnEuro;


  @Column(name = "createdAt")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updatedAt")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Advertisement() {
  }

  public Advertisement(final String title, final String description, final Category category, final BigDecimal priceLnEuro, final User user) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.category = category;
    this.pricelnEuro = priceLnEuro;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

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

  public User getUser() {
    return user;
  }

  public void setUser(final User user) {
    this.user = user;
  }


  public void setCategory(final Category category) {
    this.category = category;
  }

  public BigDecimal getPricelnEuro() {
    return pricelnEuro;
  }

  public void setPricelnEuro(final BigDecimal priceLnEuro) {
    this.pricelnEuro = priceLnEuro;
  }

  public Category getCategory() {
    return category;
  }

  public List<Images> getImages() {
    return images;
  }

  public void setImages(List<Images> images) {
    this.images = images;
  }

  @Override
  public String toString() {
    return "Advertisement{"
            + "id=" + id
            + ", title='" + title + '\''
            + ", description='" + description + '\''
            + ", user=" + user
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + '}';
  }
}
