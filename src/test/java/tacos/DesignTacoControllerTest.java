package tacos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.data.ReactiveTacoRepository;
import tacos.web.DesignTacoController;

import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

public class DesignTacoControllerTest {

    @Test
    void shouldReturnRecentTacos() {
        Taco[] tacos = {
            testTaco(1L), testTaco(2L),
            testTaco(3L), testTaco(4L),
            testTaco(5L), testTaco(6L),
            testTaco(7L), testTaco(8L),
            testTaco(9L), testTaco(10L),
            testTaco(11L), testTaco(12L),
            testTaco(13L), testTaco(14L),
            testTaco(15L), testTaco(16L),
        };

        Flux<Taco> tacoFlux = Flux.just(tacos);
        ReactiveTacoRepository tacoRepository = Mockito.mock(ReactiveTacoRepository.class);

        when(tacoRepository.findAll()).thenReturn(tacoFlux);
        WebTestClient testClient = WebTestClient.bindToController(new DesignTacoController(null, null, null)).build();

        testClient.get().uri("/design/recent")
                  .exchange()
                  .expectStatus().isOk()
                  .expectBody()
                  .jsonPath("$").isArray()
                  .jsonPath("$").isNotEmpty()
                  .jsonPath("$[0].id").isEqualTo(tacos[0].getId().toString())
                  .jsonPath("$[0].name").isEqualTo("Taco 1")
                  .jsonPath("$[1].id").isEqualTo("Taco 2")
                  .jsonPath("$[1].name").isEqualTo("Taco 2")
                  .jsonPath("$[11].id").isEqualTo(tacos[11].getId().toString())
                  .jsonPath("$[12]").doesNotExist();

    }

    @Test
    void shouldSaveATaco() {
        ReactiveTacoRepository tacoRepository = Mockito.mock(ReactiveTacoRepository.class);
        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));
        Taco savedTaco = testTaco(null);
        savedTaco.setId(1L);
        Mono<Taco> savedTacoMono = Mono.just(savedTaco);

        when(tacoRepository.save(any())).thenReturn(savedTacoMono);

        WebTestClient testClient = WebTestClient.bindToController(new DesignTacoController(null, null, null)).build();

        testClient.post()
                  .uri("/design")
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(unsavedTacoMono, Taco.class)
                  .exchange()
                  .expectStatus().isCreated()
                  .expectBody(Taco.class)
                  .isEqualTo(savedTaco);
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setId(UUID.randomUUID().timestamp());
        taco.setName("Taco " + number);
        List<IngredientUDT> ingredients = new ArrayList<>();
        ingredients.add(new IngredientUDT("INGA", Ingredient.Type.WRAP));
        ingredients.add(new IngredientUDT("INGB", Ingredient.Type.PROTEIN));
//        taco.setIngredients(ingredients);
        return taco;
    }

}
