package pl.bier.cocktail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.Category;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.ingredient.repository.IngredientRepository;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;
import pl.bier.cocktail.recipe.entity.Recipe;
import pl.bier.cocktail.recipe.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class CocktailAtHomeBackendApplication implements CommandLineRunner {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public static void main(String[] args) {
        SpringApplication.run(CocktailAtHomeBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        saveFirstIngredient();
        saveSecondIngredient();
        saveFirstRecipe();

        ingredientRepository.findAll().forEach(System.out::println);
        recipeRepository.findAll().forEach(System.out::println);
    }

    private void saveFirstIngredient() {
        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .category(Category.ALCOHOLIC_BEVERAGE)
                .build());
        HashMap<String, LocalizedIngredient> localizedIngredientHashMap = new HashMap<>();
        localizedIngredientHashMap.put("en_US",
                LocalizedIngredient.builder()
                        .name("vodka")
                        .description("Clear distilled beverage composed primarily of water and alcohol.")
                        .localizedId(new LocalizedId("en_US"))
                        .ingredient(ingredient)
                        .build());
        localizedIngredientHashMap.put("pl_PL",
                LocalizedIngredient.builder()
                        .name("wódka")
                        .description("Napój wysokoprocentowy skladający sie prawie wyłącznie z wody i alkoholu.")
                        .localizedId(new LocalizedId("pl_PL"))
                        .ingredient(ingredient)
                        .build());
        ingredient.setLocalizations(localizedIngredientHashMap);
        ingredientRepository.save(ingredient);
    }

    private void saveSecondIngredient() {
        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .category(Category.ALCOHOLIC_BEVERAGE)
                .build());
        HashMap<String, LocalizedIngredient> localizedIngredientHashMap = new HashMap<>();
        localizedIngredientHashMap.put("en_US",
                LocalizedIngredient.builder()
                        .name("whisky")
                        .description("Distilled beverage from grain mash. Usually aged in wooden casks")
                        .localizedId(new LocalizedId("en_US"))
                        .ingredient(ingredient)
                        .build());
        localizedIngredientHashMap.put("pl_PL",
                LocalizedIngredient.builder()
                        .name("whisky")
                        .description("Napój wysokoprocentowy powstający z zacieru zbożowego. Najczęściej leżakowany w beczkach.")
                        .localizedId(new LocalizedId("pl_PL"))
                        .ingredient(ingredient)
                        .build());
        ingredient.setLocalizations(localizedIngredientHashMap);
        ingredientRepository.save(ingredient);
    }

    private void saveFirstRecipe() {
        Recipe recipe = Recipe.builder()
                .build();
        recipe = recipeRepository.save(recipe);

        HashMap<String, LocalizedRecipe> localizedRecipeHashMap = new HashMap<>();
        localizedRecipeHashMap.put("en_US",
                LocalizedRecipe.builder()
                        .name("Just vodka")
                        .description("Pour vodka into the glass")
                        .localizedId(new LocalizedId("en_US"))
                        .recipe(recipe)
                        .build());
        localizedRecipeHashMap.put("pl_PL",
                LocalizedRecipe.builder()
                        .name("Czysta wódka")
                        .description("Nalej wódkę do szklanki")
                        .localizedId(new LocalizedId("pl_PL"))
                        .recipe(recipe)
                        .build());

        recipe.setLocalizations(localizedRecipeHashMap);
        recipe.setIngredientsMap(Map.of(ingredientRepository.findById(1L).get(), 100));

        recipeRepository.save(recipe);
    }

}
