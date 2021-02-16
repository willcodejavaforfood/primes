package com.echolima.primes.algo;

import com.echolima.primes.PrimesCalculator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NaivePrimesCalculator implements PrimesCalculator {
    @Override
    public List<Integer> primesUpToAndIncluding(int initial) {
        return IntStream.rangeClosed(2, initial)
                .filter(i -> IntStream.rangeClosed(2, (int) Math.sqrt(i))
                        .noneMatch(j -> i % j == 0))
                .boxed()
                .collect(Collectors.toList());
    }
}
