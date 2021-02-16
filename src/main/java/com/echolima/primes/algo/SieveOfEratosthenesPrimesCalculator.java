package com.echolima.primes.algo;

import com.echolima.primes.PrimesCalculator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SieveOfEratosthenesPrimesCalculator implements PrimesCalculator {
    @Override
    public List<Integer> primesUpToAndIncluding(int initial) {
        return runEratosthenesSieve(initial);
    }

    /**
     * Shamelessly stolen and modified from https://introcs.cs.princeton.edu/java/14array/PrimeSieve.java.html
     */
    private List<Integer> runEratosthenesSieve(int upperBound) {
        // initially assume all integers are prime
        boolean[] isPrime = new boolean[upperBound + 1];
        for (int i = 2; i <= upperBound; i++) {
            isPrime[i] = true;
        }

        // mark non-primes <= n using Sieve of Eratosthenes
        for (int factor = 2; factor*factor <= upperBound; factor++) {

            // if factor is prime, then mark multiples of factor as nonprime
            // suffices to consider mutiples factor, factor+1, ...,  n/factor
            if (isPrime[factor]) {
                for (int j = factor; factor * j <= upperBound; j++) {
                    isPrime[factor * j] = false;
                }
            }
        }

        return IntStream.rangeClosed(2, upperBound).filter(i -> isPrime[i]).boxed().collect(Collectors.toList());
    }
}
