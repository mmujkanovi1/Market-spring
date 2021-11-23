package com.example.springboot.components;

import com.example.springboot.clients.CurrencyClient;
import com.example.springboot.model.Currency;
import com.example.springboot.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Component
public class ScheduledConversionComponent {

  @Autowired
  private CurrencyRepository currencyRepository;

  @Autowired
  private CurrencyClient client;

  @Scheduled(cron = "${cronExpression}")
  public void updateCurrency() throws ParseException {
    Currency currency = new Currency();
    currency.setId(1L);
    NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
    Number number = format.parse(client.getPrize().get(0).getCurrency());
    double prizeUsd = number.doubleValue();
    currency.setUsd(prizeUsd);
    number = format.parse(client.getPrize().get(1).getCurrency());
    double prizeEur = number.doubleValue();
    currency.setEur(prizeEur);
    currencyRepository.save(currency);
  }
}
