package tacos;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

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

    @Test
    void mergeFluxes() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
                                         .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")
                                    .delaySubscription(Duration.ofMillis(250))
                                    .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);

        StepVerifier.create(mergedFlux)
            .expectNext("Garfield")
            .expectNext("Lasagna")
            .expectNext("Kojak")
            .expectNext("Lollipops")
            .expectNext("Barbossa")
            .expectNext("Apples")
            .verifyComplete();
    }

    @Test
    void zipFluxes() {

        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");

        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);
        StepVerifier.create(zippedFlux)
                    .expectNextMatches(p -> p.getT1().equals("Garfield")
                        && p.getT2().equals("Lasagna"))
                    .expectNextMatches(p -> p.getT1().equals("Kojak")
                        && p.getT2().equals("Lollipops"))
                    .expectNextMatches(p -> p.getT1().equals("Barbossa")
                        && p.getT2().equals("Apples"))
                    .verifyComplete();

    }

    @Test
    void zipFluxesToObject() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");

        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c , f) -> c + " eats " + f);

        StepVerifier.create(zippedFlux)
                    .expectNext("Garfield eats Lasagna")
                    .expectNext("Kojak eats Lollipops")
                    .expectNext("Barbossa eats Apples")
                    .verifyComplete();
    }

    @Test
    void flatMap() {
        Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
            .flatMap(n -> Mono.just(n).map(p -> {
                String[] split = p.split("\\s");
                return new Player(split[0], split[1]);
            }))
            .subscribeOn(Schedulers.parallel());

        var players = Arrays.asList(
            new Player("Michael", "Jordan"),
            new Player("Scottie", "Pippen"),
            new Player("Steve", "Kerr")
        );

        StepVerifier.create(playerFlux)
            .expectNextMatches(players::contains)
            .expectNextMatches(players::contains)
            .expectNextMatches(players::contains)
            .verifyComplete();
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    static class Player {
        private final String firstName;
        private final String lastName;
    }

}
