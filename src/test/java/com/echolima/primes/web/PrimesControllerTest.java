package com.echolima.primes.web;

import com.echolima.primes.PrimesCalculator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class PrimesControllerTest {
    private static final String INVALID_ALGORITHM = "invalidAlgorithm";
    private static final String VALID_ALGORITHM = "validAlgorithm";

    private PrimesController primesController;
    private ImmutableMap<String, PrimesCalculator> primesCalculatorMap = Mockito.mock(ImmutableMap.class);
    private PrimesCalculator primesCalculator = Mockito.mock(PrimesCalculator.class);

    @Before
    public void setup() {
        primesController = new PrimesController(primesCalculatorMap);
    }

    @Test(expected = PrimesController.BadRequestException.class)
    public void shouldThrowBadRequestForInvalidAlgorithm() {
        given(primesCalculatorMap.containsKey(INVALID_ALGORITHM)).willReturn(false);

        primesController.primes(3, INVALID_ALGORITHM);
    }

    @Test(expected = PrimesController.BadRequestException.class)
    public void shouldThrowBadRequestForNegativeNumbers() {
        given(primesCalculatorMap.containsKey(VALID_ALGORITHM)).willReturn(true);

        primesController.primes(-1, VALID_ALGORITHM);
    }

    @Test
    public void shouldReturnPrimesForValidRequest() {
        given(primesCalculatorMap.containsKey(VALID_ALGORITHM)).willReturn(true);
        given(primesCalculatorMap.get(VALID_ALGORITHM)).willReturn(primesCalculator);
        given(primesCalculator.primesUpToAndIncluding(10)).willReturn(Lists.newArrayList(2, 3, 5, 7));

        PrimesResponse primesResponse = primesController.primes(10, VALID_ALGORITHM);

        assertThat(primesResponse.getInitial(), is(10));
        assertThat(primesResponse.getPrimes(), is(Lists.newArrayList(2, 3, 5, 7)));
    }

}