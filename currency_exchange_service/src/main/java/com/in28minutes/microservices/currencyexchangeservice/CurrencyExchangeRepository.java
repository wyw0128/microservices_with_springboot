package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    // can do a findBy + the column name that you want to find by:
    // so spring data jpa will process will convert this to a query
    CurrencyExchange findByFromAndTo(String from, String to);
}
