package pl.bier.cocktail.ingredient.repository;

import org.springframework.data.repository.CrudRepository;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;

import java.util.List;
import java.util.Optional;

public interface LocalizedIngredientRepository extends CrudRepository<LocalizedIngredient, LocalizedId> {

    List<LocalizedIngredient> findByNameContainingAndLocalizedIdLocale(String fragment, Locale locale);

    List<LocalizedIngredient> findByLocalizedIdLocale(Locale locale);

    Optional<LocalizedIngredient> findByLocalizedIdIdAndLocalizedIdLocale(Long id, Locale locale);

}
