package tacos.web.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import tacos.Ingredient;

import java.util.Date;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class TacoResource extends RepresentationModel {
    private final String name;
    private final Date createdAt;
    private final List<Ingredient> ingredients;
}
