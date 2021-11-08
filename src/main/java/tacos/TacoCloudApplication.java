package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.util.List;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository,
                                        UserRepository userRepository,
                                        TacoRepository tacoRepository) {
        return args -> {
            var flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
            ingredientRepository.save(flourTortilla);
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
            userRepository.save(new User("user1", new BCryptPasswordEncoder().encode("password1"),
                    "Spring Genius", "Street", "city", "state", "zip", "phoneNumber"));
            tacoRepository.save(Taco.builder()
                                    .name("delicious taco1")
                                    .ingredients(List.of(flourTortilla))
                                    .build());
            tacoRepository.save(Taco.builder()
                                    .name("delicious taco2")
                                    .ingredients(List.of(flourTortilla))
                                    .build());
            tacoRepository.save(Taco.builder()
                                    .name("delicious taco3")
                                    .ingredients(List.of(flourTortilla))
                                    .build());
            tacoRepository.save(Taco.builder()
                                    .name("delicious taco4")
                                    .ingredients(List.of(flourTortilla))
                                    .build());
            tacoRepository.save(Taco.builder()
                                    .name("delicious taco5")
                                    .ingredients(List.of(flourTortilla))
                                    .build());
            tacoRepository.save(Taco.builder()
                                    .name("delicious taco6")
                                    .ingredients(List.of(flourTortilla))
                                    .build());
        };
    }

}
