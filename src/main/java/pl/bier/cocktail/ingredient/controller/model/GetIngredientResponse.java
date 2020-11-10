package pl.bier.cocktail.ingredient.controller.model;

import lombok.Data;

@Data
public class GetIngredientResponse {

    private long id;

    private String name;

    private String description;

}
