package com.echolima.primes;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrimesApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PrimesController primesController;

    @Test
    public void contextLoads() {
        assertThat(primesController, notNullValue());
    }

    @Test
    public void oneIsNotAPrime() {
        ResponseEntity<PrimesResponse> responseEntity = makePrimesRequest(1);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PrimesResponse body = responseEntity.getBody();
        assertThat(body.getInitial(), is(1));
        assertThat(body.getPrimes().isEmpty(), is(true));
    }

    @Test
    public void shouldReturnTwoAsTheFirstPrime() {
        ResponseEntity<PrimesResponse> responseEntity = makePrimesRequest(2);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PrimesResponse body = responseEntity.getBody();
        assertThat(body.getInitial(), is(2));
        assertThat(body.getPrimes(), is(Lists.newArrayList(2)));
    }

    @Test
    public void shouldReturnPrimesLessThan11() {
        ResponseEntity<PrimesResponse> responseEntity = makePrimesRequest(10);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PrimesResponse body = responseEntity.getBody();
        assertThat(body.getInitial(), is(10));
        assertThat(body.getPrimes(), is(Lists.newArrayList(2, 3, 5, 7)));
    }

    @Test
    public void initialShouldBeInclusive() {
        ResponseEntity<PrimesResponse> responseEntity = makePrimesRequest(3);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PrimesResponse body = responseEntity.getBody();
        assertThat(body.getInitial(), is(3));
        assertThat(body.getPrimes(), is(Lists.newArrayList(2, 3)));
    }

    @Test
    public void shouldAllowOtherAlgorithms() {
        String uriString = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/primes/%d", port, 10))
                .queryParam("algorithm", "eratosthenes")
                .toUriString();

        ResponseEntity<PrimesResponse> responseEntity = restTemplate.getForEntity(uriString, PrimesResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        PrimesResponse body = responseEntity.getBody();
        assertThat(body.getInitial(), is(10));
        assertThat(body.getPrimes(), is(Lists.newArrayList(2, 3, 5, 7)));
    }

    @Test
    public void invalidAlgorithmResultsInBadRequest() {
        String uriString = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/primes/%d", port, 10))
                .queryParam("algorithm", "notarealalgorithm")
                .toUriString();

        ResponseEntity<PrimesResponse> responseEntity = restTemplate.getForEntity(uriString, PrimesResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void negativeInitialResultsInBadRequest() {
        String uriString = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/primes/%d", port, -10))
                .queryParam("algorithm", "naive")
                .toUriString();

        ResponseEntity<PrimesResponse> responseEntity = restTemplate.getForEntity(uriString, PrimesResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<PrimesResponse> makePrimesRequest(int initial) {
        return restTemplate.getForEntity(
                String.format("http://localhost:%d/primes/%d", port, initial),
                PrimesResponse.class);
    }

}
