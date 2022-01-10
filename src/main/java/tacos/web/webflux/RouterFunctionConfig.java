package tacos.web.webflux;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.data.ReactiveTacoRepository;
import tacos.data.TacoRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    @Autowired
    private ReactiveTacoRepository tacoRepository;

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/design/taco"), this::recents)
            .andRoute(POST("/design"), this::postTaco);
    }

    public Mono<ServerResponse> recents(ServerRequest serverRequest) {
        return ServerResponse.ok().body(tacoRepository.findAll().take(12), Taco.class);
    }

    private Mono<ServerResponse> postTaco(ServerRequest serverRequest) {
        Mono<Taco> taco = serverRequest.bodyToMono(Taco.class);
        Mono<Taco> savedTaco = tacoRepository.save(taco.block());
        return ServerResponse
            .created(URI.create("http://localhost:8080/design/taco/" + savedTaco.map(Taco::getId)))
            .body(savedTaco, Taco.class);
    }

}
