package com.example.springboot.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity(name = "Roles")
@Table
public class Role {

  @Id
  @SequenceGenerator(
          name = "role_sequence",
          sequenceName = "role_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "role_sequence"
  )
  @Column(
          name = "id"
  )
  private Long id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private RoleType name;

  @OneToMany(mappedBy = "userRole")
  private List<User> users = new ArrayList<>();

  public Role() {
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public RoleType getName() {
    return name;
  }

  public void setName(final RoleType name) {
    this.name = name;
  }


}
