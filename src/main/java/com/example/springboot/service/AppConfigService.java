package com.example.springboot.service;

import com.example.springboot.model.AppConfig;
import com.example.springboot.repository.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppConfigService {

  @Autowired
  private AppConfigRepository appConfigRepository;

  private static final int NUMBER_FOR_LIST = 0;

  public AppConfig getOneAppConfig() {
    return appConfigRepository.findAll().get(NUMBER_FOR_LIST);
  }
}
