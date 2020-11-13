package pl.bier.cocktail.recipe.controller.model;

import lombok.Builder;
import lombok.Data;
import pl.bier.cocktail.common.constraints.DefaultLanguagePresent;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.controller.model.PostIngredientRequest;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;
import pl.bier.cocktail.recipe.entity.Recipe;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class PostRecipeRequest {

    private Map<Long, Double> ingredients;

    @DefaultLanguagePresent
    private Map<Locale, Localization> localizations;

    @Data
    public static class Localization {

        private String name;

        private String description;

    }

}
