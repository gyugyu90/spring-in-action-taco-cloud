package tacos.web.api;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import tacos.Taco;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {

    public TacoResourceAssembler(Class<?> controllerClass, Class<TacoResource> resourceType) {
        super(controllerClass, resourceType);
    }

    @Override
    protected TacoResource instantiateModel(Taco entity) {
        return new TacoResource(entity);
    }

    @Override
    public TacoResource toModel(Taco entity) {
        return createModelWithId(entity.getId(), entity);
    }
}
