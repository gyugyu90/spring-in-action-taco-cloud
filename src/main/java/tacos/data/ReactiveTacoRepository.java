package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import tacos.Taco;

public interface ReactiveTacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
