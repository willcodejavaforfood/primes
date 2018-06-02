To start the server: mvn exec:java

I've created a prime calculating service which is extendable with algorithms for the actual calculations.

PrimesApplicationsTests - Starts the server and performs http tests on the service

PrimesCalculatorTester - Unit tests which takes instances of PrimesCalculator to perform the same tests on them

PrimesControllerTest - Unit test for the controller. Normally I'd use spring-test to at least also test the http layer through the MockMVC.

Swagger was added, but with some difficulty. At the moment it is not very compatible with Spring Boot 2.
