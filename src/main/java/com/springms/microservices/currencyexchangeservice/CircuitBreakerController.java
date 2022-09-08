package com.springms.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name="default")
//    @Retry(name="myconfig", fallbackMethod = "fallBackResponse")
//    @CircuitBreaker(name="myconfig", fallbackMethod = "fallBackResponse")
//    @RateLimiter(name="default")
    @Bulkhead(name="/sample-api")
    public String sampleAPI() {
        logger.info("sample-api call");
        ResponseEntity<String> errRequest = new RestTemplate().getForEntity("http://localhost:8000/errorUrl", String.class);
        return errRequest.getBody();
//        return "some sample API";
    }

    public String fallBackResponse(Exception e) {
        return "fallback response: " + e.getMessage();
    }
}
