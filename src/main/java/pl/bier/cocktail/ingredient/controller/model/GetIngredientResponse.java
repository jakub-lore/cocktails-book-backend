package pl.bier.cocktail.ingredient.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetIngredientResponse {

    private IngredientDto ingredient;

}
