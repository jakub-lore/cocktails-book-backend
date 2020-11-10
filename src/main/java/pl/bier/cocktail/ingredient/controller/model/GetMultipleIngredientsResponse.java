package pl.bier.cocktail.ingredient.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMultipleIngredientsResponse {

    private List<IngredientDto> ingredients;

}
