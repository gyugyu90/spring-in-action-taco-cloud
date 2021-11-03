package tacos.web.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import tacos.Ingredient;
import tacos.Taco;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class TacoResource extends RepresentationModel<TacoResource> {

    private static final IngredientResourceAssembler INGREDIENT_ASSEMBLER = new IngredientResourceAssembler();

    private final String name;
    private final Date createdAt;
    private final List<IngredientResource> ingredients;

    public TacoResource(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = new ArrayList<>(INGREDIENT_ASSEMBLER.toCollectionModel(taco.getIngredients()).getContent());
    }
}
