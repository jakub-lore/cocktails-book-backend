package pl.bier.cocktail.ingredient.controller.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import pl.bier.cocktail.common.constraints.DefaultLanguagePresent;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Validated
@Builder
public class PostIngredientRequest {

    @DefaultLanguagePresent
    private Map<Locale, Localization> localizations;

    private Category category;

    @Min(0)
    @Max(1000)
    private int alcoholByVolumePerMil;

    @Data
    @Validated
    @Builder
    public static class Localization {

        private String name;

        private String description;

    }

    public static Function<PostIngredientRequest, Ingredient> requestToEntityMapper() {
        return request -> {
            Ingredient ingredient = Ingredient.builder()
                    .category(request.getCategory())
                    .alcoholByVolumePerMil(request.alcoholByVolumePerMil)
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


