package pl.bier.cocktail.ingredient.controller.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import pl.bier.cocktail.common.constraints.DefaultLanguagePresent;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.Category;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Validated
public class PostIngredientRequest {

    @DefaultLanguagePresent
    private Map<Locale, Localization> localizations;

    private Category category;

    @Data
    @Validated
    public static class Localization {

        private String name;

        private String description;

    }

    public static Function<PostIngredientRequest, Ingredient> requestToEntityMapper() {
        return request -> {
            Ingredient ingredient = Ingredient.builder()
                    .category(request.getCategory())
                    .build();
            ingredient.setLocalizations(
                    request.getLocalizations().entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey,
                                    e -> LocalizedIngredient.builder()
                                            .ingredient(ingredient)
                                            .name(e.getValue().getName())
                                            .description(e.getValue().getDescription())
                                            .localizedId(new LocalizedId(e.getKey()))
                                            .build())));
            return ingredient;
        };
    }

}


