package tacos.web.api;


import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/ingredients", produces="application/json")
@CrossOrigin(origins="*")
public class IngredientApiController {

    private final IngredientRepository ingredientRepository;

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public EntityModel<Ingredient> findById(@PathVariable("id") String id) {
        var ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found"));
        return EntityModel.of(ingredient);
    }

}
