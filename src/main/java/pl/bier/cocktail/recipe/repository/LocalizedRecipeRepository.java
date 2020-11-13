package pl.bier.cocktail.recipe.repository;

import org.springframework.data.repository.CrudRepository;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;

import java.util.List;
import java.util.Optional;

public interface LocalizedRecipeRepository extends CrudRepository<LocalizedRecipe, LocalizedId> {

    Optional<LocalizedRecipe> findByLocalizedIdIdAndLocalizedIdLocale(Long id, Locale locale);

    List<LocalizedRecipe> findByLocalizedIdLocale(Locale locale);

    List<LocalizedRecipe> findByNameContainingAndLocalizedIdLocale(String fragment, Locale locale);
}
