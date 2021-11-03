package tacos.web.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import tacos.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientResourceAssembler extends RepresentationModelAssemblerSupport<Ingredient, IngredientResource> {

    public IngredientResourceAssembler() {
        super(IngredientApiController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toModel(Ingredient entity) {
        return createModelWithId(entity.getId(), entity);
    }

//    @Override
//    public CollectionModel<IngredientResource> toCollectionModel(Iterable<? extends Ingredient> entities) {
//        List<IngredientResource> list = new ArrayList<>();
//        for (Ingredient entity : entities) {
//            list.add(toModel(entity));
//        }
//        return CollectionModel.of(list);
//    }

    @Override
    protected IngredientResource instantiateModel(Ingredient entity) {
        return new IngredientResource(entity);
    }
}
