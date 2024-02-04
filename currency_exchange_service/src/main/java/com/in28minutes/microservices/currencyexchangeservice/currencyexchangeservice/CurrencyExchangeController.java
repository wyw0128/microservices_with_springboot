package com.in28minutes.microservices.currencyexchangeservice.currencyexchangeservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    // we create the from and to as path variables, so others can path into variables they want
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        return new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
    }
}
