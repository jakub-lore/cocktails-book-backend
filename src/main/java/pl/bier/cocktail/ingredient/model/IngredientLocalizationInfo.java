package pl.bier.cocktail.ingredient.model;

import lombok.Data;
import pl.bier.cocktail.common.entity.Locale;

import java.util.List;

@Data
public class IngredientLocalizationInfo {

    private Long id;

    private List<Locale> availableLanguages;

}
