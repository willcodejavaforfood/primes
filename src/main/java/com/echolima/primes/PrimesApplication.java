package com.echolima.primes;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrimesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimesApplication.class, args);
    }

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public PrimesController primesController() {
        return new PrimesController(ImmutableMap.<String, PrimesCalculator>builder()
                .put("naive", new NaivePrimesCalculator())
                .put("eratosthenes", new SieveOfEratosthenesPrimesCalculator())
                .build());
    }
}
