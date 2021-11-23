package com.example.springboot.service;

import com.example.springboot.components.ScheduledConversionComponent;
import com.example.springboot.model.Currency;
import com.example.springboot.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@Service
public class ScheduledConversionService {

  @Autowired
  private ScheduledConversionComponent scheduledConversionComponent;

  @Autowired
  private CurrencyRepository currencyRepository;

  private static final int NUMBER_FOR_LIST = 0;

  public BigDecimal eurIntoUsd(final BigDecimal prizelnEuro) throws ParseException {
    List<Currency> currencyList = currencyRepository.findAll();
    if (currencyList.size() == 0) {
      scheduledConversionComponent.updateCurrency();
    }

    Currency currency = currencyRepository.findAll().get(NUMBER_FOR_LIST);

    Double euroIntoUsd = currency.getEur() / currency.getUsd();

    BigDecimal usdPrice = prizelnEuro.multiply(BigDecimal.valueOf(euroIntoUsd));
    return usdPrice;
  }


}
