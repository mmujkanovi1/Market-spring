package com.example.springboot.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserRequest {

  private static final int MIN_NAME_SIZE = 3;
  private static final int NUMBER_SIZE = 10;

  @NotEmpty
  @Size(min = MIN_NAME_SIZE, message = "user name should have at least 3 characters")
  private String displayName;

  @NotEmpty
  @Size(min = MIN_NAME_SIZE, message = "user surname should have at least 3 characters")
  private String displaySurname;

  @Size(min = NUMBER_SIZE, max = NUMBER_SIZE, message = "Phone number should have 10 numbers")
  @Pattern(regexp = "(^$|[0-9]{10})")
  private String phoneNumber;

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  private String username;

  @Size(min = MIN_NAME_SIZE, message = "user password should have at least 3 characters")
  private String password;

  @NotNull
  private Long roleId;

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

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(final Long roleId) {
    this.roleId = roleId;
  }
}
