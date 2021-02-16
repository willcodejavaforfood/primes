package com.echolima.primes.web;

import com.echolima.primes.PrimesCalculator;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimesController {
    private static final Logger logger = LoggerFactory.getLogger(PrimesController.class);

    private final ImmutableMap<String, PrimesCalculator> primeCalculators;

    @Autowired
    public PrimesController(@Qualifier("algos") ImmutableMap<String, PrimesCalculator> primeCalculators) {
        this.primeCalculators = primeCalculators;
    }

    @GetMapping("/primes/{initial}")
    public PrimesResponse primes(
            @PathVariable("initial") Integer initial,
            @RequestParam(value = "algorithm", required = true, defaultValue = "naive") String algorithm) {
        if (!primeCalculators.containsKey(algorithm)) {
            logger.warn("Invalid algorithm: [{}]", algorithm);
            throw new BadRequestException(String.format("algorithm: [%s] is invalid", algorithm));
        }
        if (initial < 0) {
            throw new BadRequestException("This will work much better with a positive value for initial");
        }
        return new PrimesResponse(initial, primeCalculators.get(algorithm).primesUpToAndIncluding(initial));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}
