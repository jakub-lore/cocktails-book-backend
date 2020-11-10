package pl.bier.cocktail.recipe.repository;

import org.springframework.data.repository.CrudRepository;
import pl.bier.cocktail.recipe.entity.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
