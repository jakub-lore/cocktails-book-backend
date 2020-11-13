package pl.bier.cocktail.recipe.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRecipeResponse {

    private RecipeDto recipe;

}
