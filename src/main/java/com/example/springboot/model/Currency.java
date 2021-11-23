package com.example.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Currency")
@Table
public class Currency {
  @Id
  @Column(
          name = "id"
  )
  private Long id;

  @Column(
          name = "usd"
  )
  private Double usd;

  @Column(
          name = "eur"
  )
  private Double eur;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Double getUsd() {
    return usd;
  }

  public void setUsd(final Double usd) {
    this.usd = usd;
  }

  public Double getEur() {
    return eur;
  }

  public void setEur(final Double eur) {
    this.eur = eur;
  }
}
