package com.example.springboot.clients;


import com.example.springboot.response.CurrencyConversionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(url = "${url}", name = "CURRENCY-CLIENT")
public interface CurrencyClient {

  @RequestMapping(method = RequestMethod.GET, value = "${value}")
  List<CurrencyConversionResponse> getPrize();


}
