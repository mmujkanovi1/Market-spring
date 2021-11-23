package com.example.springboot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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


@Entity(name = "Users")
@Table(name = "users")
public class User {

  @Id
  @SequenceGenerator(
          name = "user_sequence",
          sequenceName = "user_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "user_sequence"
  )
  @Column(
          name = "id"
  )
  private Long id;

  @Column(name = "name")
  private String displayName;

  @Column(name = "Surname")
  private String displaySurname;

  @Column(name = "phoneNumber")
  private String phoneNumber;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @OneToMany(mappedBy = "user")
  private List<Advertisement> advertisements = new ArrayList<Advertisement>();

  @OneToMany(mappedBy = "userCategory")
  private List<Category> categories = new ArrayList<Category>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  @JsonIgnore
  private Role userRole;

  @Column(name = "createdAt")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updatedAt")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public List<Advertisement> getAdvertisements() {
    return advertisements;
  }

  public void setAdvertisements(final List<Advertisement> advertisements) {
    this.advertisements = advertisements;
  }

  public User() {
  }

  public User (final String displayName, final String displaySurname, final String phoneNumber, final String email, final String username, final String password, final Role role) {
    this.displayName = displayName;
    this.displaySurname = displaySurname;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.username = username;
    this.password = password;
    this.userRole = role;
  }

  public Long getUserId() {
    return id;
  }

  public void setUserId(final Long userId) {
    this.id = userId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public String getDisplaySurname() {
    return displaySurname;
  }

  public void setDisplaySurname(final String displaySurname) {
    this.displaySurname = displaySurname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Role getUserRole() {
    return userRole;
  }

  public void setUserRole(final Role userRole) {
    this.userRole = userRole;
  }

  @Override
  public String toString() {
    return "User{"
            + "id=" + id
            + ", displayName='" + displayName + '\''
            + ", displaySurname='" + displaySurname + '\''
            + ", phoneNumber='" + phoneNumber + '\''
            + ", email='" + email + '\''
            + ", createdAt=" + createdAt
            + ", updatedAt=" + updatedAt
            + '}';
  }
}
