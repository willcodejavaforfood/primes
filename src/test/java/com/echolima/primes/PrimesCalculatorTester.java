package com.echolima.primes;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class PrimesCalculatorTester {
    @Parameterized.Parameters
    public static Object[] data() {
        return new Object[] { new NaivePrimesCalculator(), new SieveOfEratosthenesPrimesCalculator() };
    }

    private PrimesCalculator primesCalculator;

    public PrimesCalculatorTester(PrimesCalculator primesCalculator) {
        this.primesCalculator = primesCalculator;
    }

    @Test
    public void oneIsNotAPrime() {
        assertThat(primesCalculator.primesUpToAndIncluding(1).isEmpty(), is(true));
    }

    @Test
    public void shouldReturnPrimesUpToALimit() {
        assertThat(primesCalculator.primesUpToAndIncluding(12), is(Lists.newArrayList(2, 3, 5, 7, 11)));
    }

    @Test
    public void shouldTreatInitialAsInclusive() {
        assertThat(primesCalculator.primesUpToAndIncluding(13), is(Lists.newArrayList(2, 3, 5, 7, 11, 13)));
    }
}
