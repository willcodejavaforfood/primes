package com.echolima.primes.config;

import com.echolima.primes.PrimesCalculator;
import com.echolima.primes.algo.NaivePrimesCalculator;
import com.echolima.primes.algo.SieveOfEratosthenesPrimesCalculator;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrimesContext {
    @Bean(name = "algos")
    public ImmutableMap<String, PrimesCalculator> primesController() {
        return ImmutableMap.<String, PrimesCalculator>builder()
                .put("naive", new NaivePrimesCalculator())
                .put("eratosthenes", new SieveOfEratosthenesPrimesCalculator())
                .build();
    }
}
