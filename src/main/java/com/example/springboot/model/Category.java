package com.example.springboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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


@Entity(name = "Category")
@Table(name = "category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {


  @Id
  @SequenceGenerator(
          name = "category_sequence",
          sequenceName = "category_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "category_sequence"
  )
  @Column(
          name = "id"
  )
  private Long id;

  @Column(
          name = "categoryName"
  )
  private String categoryName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User userCategory;

  @OneToMany(mappedBy = "category")
  private List<Advertisement> advertisements = new ArrayList<Advertisement>();

  @Column(name = "createdAt")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updatedAt")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Category() {
  }

  public Category(final String categoryName) {
    this.categoryName = categoryName;
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

  public void setUser(final User user) {
    this.userCategory = user;
  }

  public User getUser() {
    return userCategory;
  }

  public void setAdvertisements(final List<Advertisement> advertisements) {
    this.advertisements = advertisements;
  }

  @Override
  public String toString() {
    return "Category{"
            + "id=" + id
            + ", categoryName='" + categoryName + '\''
            + ", user=" + userCategory
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + '}';
  }
}
