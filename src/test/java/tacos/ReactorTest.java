package tacos;

import java.time.Duration;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ReactorTest {

    @Test
    void testMono() {
        Mono.just("Craig")
            .map(n -> n.toUpperCase(Locale.ROOT))
            .map(cn -> "Hello, " + cn + "!")
            .subscribe(System.out::println);
    }

    @Test
    void createAFluxJust() {
        Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        fruitFlux.subscribe(f -> System.out.println("Here's some fruit: " + f));
        StepVerifier.create(fruitFlux)
            .expectNext("Apple")
            .expectNext("Orange")
            .expectNext("Grape")
            .expectNext("Banana")
            .expectNext("Strawberry")
            .verifyComplete();
    }

    @Test
    void createAFluxFromArray() {
        String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

        Flux<String> fruitFlux = Flux.fromArray(fruits);

        StepVerifier.create(fruitFlux)
                    .expectNext("Apple")
                    .expectNext("Orange")
                    .expectNext("Grape")
                    .expectNext("Banana")
                    .expectNext("Strawberry")
                    .verifyComplete();
    }

    @Test
    void createAFluxRange() {
        Flux<Integer> intervalFlux = Flux.range(1, 5);

        StepVerifier.create(intervalFlux)
                    .expectNext(1)
                    .expectNext(2)
                    .expectNext(3)
                    .expectNext(4)
                    .expectNext(5)
                    .verifyComplete();
    }

    @Test
    void createAFluxInterval() {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1))
                                      .take(5);

        StepVerifier.create(intervalFlux)
                    .expectNext(0L) // 0부터
                    .expectNext(1L)
                    .expectNext(2L)
                    .expectNext(3L)
                    .expectNext(4L)
                    .verifyComplete();
    }
}
