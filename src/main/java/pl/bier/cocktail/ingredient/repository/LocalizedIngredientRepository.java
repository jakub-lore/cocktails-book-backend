package pl.bier.cocktail.ingredient.repository;

import org.springframework.data.repository.CrudRepository;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;

import java.util.List;
import java.util.Optional;

public interface LocalizedIngredientRepository extends CrudRepository<LocalizedIngredient, LocalizedId> {

    List<LocalizedIngredient> findByNameStartingWithAndLocalizedIdLocale(String start, String locale);

    Optional<LocalizedIngredient> findByLocalizedIdIdAndLocalizedIdLocale(Long id, String locale);

}
