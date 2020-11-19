package pl.bier.cocktail.recipe.controller.model;

import lombok.Builder;
import lombok.Data;
import pl.bier.cocktail.common.entity.Locale;

import java.util.Map;

@Data
@Builder
public class RecipeDto {

    private long id;

    private String name;

    private String description;

    private Locale locale;

    private Map<Long, Double> ingredients;

}
