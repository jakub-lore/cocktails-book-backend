package pl.bier.cocktail.ingredient.model;

import lombok.Data;

import java.util.List;

@Data
public class IngredientInfo {

    private Long id;

    private List<String> availableLanguages;

}
