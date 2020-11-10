package pl.bier.cocktail.ingredient.controller.model;

import lombok.Builder;
import lombok.Data;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;

import java.util.function.Function;

@Data
@Builder
public class IngredientDto {

    private long id;

    private String name;

    private String description;

    public static Function<LocalizedIngredient, IngredientDto> entityToDtoMapper() {
        return localizedIngredient -> IngredientDto.builder()
                .description(localizedIngredient.getDescription())
                .name(localizedIngredient.getName())
                .id(localizedIngredient.getLocalizedId().getId())
                .build();
    }

}
