package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import tacos.data.TacoRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RepositoryRestController
@RequiredArgsConstructor
public class RecentTacosController {

    private final TacoRepository tacoRepository;

    @GetMapping(value = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> recentTacos() {
        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());

        var tacos = tacoRepository.findAll(pageRequest).getContent();
        var tacoResources = new TacoResourceAssembler().toCollectionModel(tacos);
        tacoResources.add(WebMvcLinkBuilder.linkTo(methodOn(DesignTacoApiController.class).recentTacos())
                                           .withRel("recents"));
        return ResponseEntity.ok(tacoResources);
    }

}
