package pl.bier.cocktail.ingredient.repository.model;

import org.springframework.data.repository.CrudRepository;
import pl.bier.cocktail.ingredient.entity.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
