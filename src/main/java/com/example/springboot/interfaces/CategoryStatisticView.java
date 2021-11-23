package com.example.springboot.interfaces;

import java.math.BigDecimal;

public interface CategoryStatisticView {

  Long getId();

  String getCategoryName();

  BigDecimal getPricePerCategory();

  Integer getNumberOfAdvertisementsPerCategory();



}
