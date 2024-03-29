package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// must be an interface, not class
// the name you give normally is the application you want to call in here
//@FeignClient(name = "currency-exchange", url = "localhost:8000")
// we can just give the name of the service that let the feign to talk to Eureka and pick up the instances of currency exchange
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
