package pl.bier.cocktail.recipe.controller.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import pl.bier.cocktail.common.constraints.DefaultLanguagePresent;
import pl.bier.cocktail.common.entity.Locale;

import java.util.Map;

@Data
@Builder
@Validated
public class PostRecipeRequest {

    private Map<Long, Double> ingredients;

    @DefaultLanguagePresent
    private Map<Locale, Localization> localizations;

    @Data
    @Builder
    @Validated
    public static class Localization {

        private String name;

        private String description;

    }

}
