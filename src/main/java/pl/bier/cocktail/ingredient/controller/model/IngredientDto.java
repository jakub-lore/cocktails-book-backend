package pl.bier.cocktail.ingredient.controller.model;

import lombok.Builder;
import lombok.Data;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;

import java.util.function.Function;

@Data
@Builder
public class IngredientDto {

    private long id;

    private String name;

    private String description;

    private Locale locale;

    public static Function<LocalizedIngredient, IngredientDto> entityToDtoMapper() {
        return localizedIngredient -> IngredientDto.builder()
                .description(localizedIngredient.getDescription())
                .name(localizedIngredient.getName())
                .locale(localizedIngredient.getLocalizedId().getLocale())
                .id(localizedIngredient.getLocalizedId().getId())
                .build();
    }

}
