package tacos.data;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        String sql = "insert into Taco(name, createdAt) values (?, ?)";
        var factory = new PreparedStatementCreatorFactory(sql, Types.VARCHAR, Types.TIMESTAMP);
        PreparedStatementCreator creator = factory.newPreparedStatementCreator(List.of(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbcTemplate.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredient.getId());
    }
}
