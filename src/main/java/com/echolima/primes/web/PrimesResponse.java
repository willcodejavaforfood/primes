package com.echolima.primes.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class PrimesResponse {
    private Integer initial;
    private List<Integer> primes;

    public PrimesResponse() {
    }

    @JsonCreator
    public PrimesResponse(
            @JsonProperty("initial") Integer initial,
            @JsonProperty("primes") List<Integer> primes) {
        this.initial = initial;
        this.primes = primes;
    }

    public Integer getInitial() {
        return initial;
    }

    public void setInitial(Integer initial) {
        this.initial = initial;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public void setPrimes(List<Integer> primes) {
        this.primes = primes;
    }
}
