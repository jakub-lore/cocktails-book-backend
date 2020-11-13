package pl.bier.cocktail.recipe.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMultipleRecipeResponse {

    private List<RecipeDto> recipes;

}
