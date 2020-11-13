package pl.bier.cocktail.recipe.controller.model;

import lombok.Builder;
import lombok.Data;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class RecipeDto {

    private long id;

    private String name;

    private String description;

    private Locale locale;

    private Map<IngredientDto, Double> ingredients;

    public static Function<LocalizedRecipe, RecipeDto> entityToDtoMapper() {
        return localizedRecipe -> RecipeDto.builder()
                .description(localizedRecipe.getDescription())
                .name(localizedRecipe.getName())
                .locale(localizedRecipe.getLocalizedId().getLocale())
                .id(localizedRecipe.getLocalizedId().getId())
                .build();
        //TODO: map ingredients

    }

}
