package com.springms.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
//        CurrencyExchange ce = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
        logger.info("from: {}, to: {}", from, to);
        CurrencyExchange ce = currencyExchangeRepository.findByFromAndTo(from, to);
        if (ce == null) {
            throw new RuntimeException("Unable to find data for " + from  + " and " + to);
        }
        ce.setEnvironment(environment.getProperty("local.server.port"));
        return ce;
    }

}
