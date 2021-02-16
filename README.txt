To start the server: mvn exec:java (./mvnw exec:java for bundled maven)

I've created a prime calculating service which is extendable with algorithms for the actual calculations.
    - http://localhost:8080/primes/{initial}?algorithm=naive
    - algorithms supported: naive, eratosthenes

PrimesApplicationsTests - Starts the server and performs http tests on the service

PrimesCalculatorTester - Unit tests which takes instances of PrimesCalculator to perform the same tests on them

PrimesControllerTest - Unit test for the controller.

Using SpringDoc as Swagger can still be rather difficult to get working with Spring Boot.
    - http://localhost:8080/swagger-ui/index.html
    - http://localhost:8080/v3/api-docs

The MediaType of the response can be controlled via the Accept header, sadly this seems to default to XML in a browser, so I recommend testing this in swagger ui or via curl
    - curl -H "Accept: application/json" http://localhost:8080/primes/10

