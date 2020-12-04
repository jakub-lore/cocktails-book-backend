package pl.bier.cocktail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.controller.model.Category;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.ingredient.repository.IngredientRepository;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;
import pl.bier.cocktail.recipe.entity.Recipe;
import pl.bier.cocktail.recipe.repository.RecipeRepository;

import java.util.HashMap;
import java.util.Map;

public class InitialDataGenerator implements CommandLineRunner {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

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
        HashMap<Locale, LocalizedIngredient> localizedIngredientHashMap = new HashMap<>();
        localizedIngredientHashMap.put(Locale.EN_US,
                LocalizedIngredient.builder()
                        .name("vodka")
                        .description("Clear distilled beverage composed primarily of water and alcohol.")
                        .localizedId(new LocalizedId(Locale.EN_US))
                        .ingredient(ingredient)
                        .build());
        localizedIngredientHashMap.put(Locale.PL,
                LocalizedIngredient.builder()
                        .name("wódka")
                        .description("Napój wysokoprocentowy skladający sie prawie wyłącznie z wody i alkoholu.")
                        .localizedId(new LocalizedId(Locale.PL))
                        .ingredient(ingredient)
                        .build());
        ingredient.setLocalizations(localizedIngredientHashMap);
        ingredientRepository.save(ingredient);
    }

    private void saveSecondIngredient() {
        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .category(Category.ALCOHOLIC_BEVERAGE)
                .build());
        HashMap<Locale, LocalizedIngredient> localizedIngredientHashMap = new HashMap<>();
        localizedIngredientHashMap.put(Locale.EN_US,
                LocalizedIngredient.builder()
                        .name("whisky")
                        .description("Distilled beverage from grain mash. Usually aged in wooden casks")
                        .localizedId(new LocalizedId(Locale.EN_US))
                        .ingredient(ingredient)
                        .build());
        ingredient.setLocalizations(localizedIngredientHashMap);
        localizedIngredientHashMap.put(Locale.PL,
                LocalizedIngredient.builder()
                        .name("whisky")
                        .description("Napój wysokoprocentowy powstający z zacieru zbożowego. Najczęściej leżakowany w beczkach.")
                        .localizedId(new LocalizedId(Locale.PL))
                        .ingredient(ingredient)
                        .build());
        ingredientRepository.save(ingredient);
    }

    private void saveFirstRecipe() {
        Recipe recipe = Recipe.builder()
                .build();
        recipe = recipeRepository.save(recipe);

        HashMap<Locale, LocalizedRecipe> localizedRecipeHashMap = new HashMap<>();
        localizedRecipeHashMap.put(Locale.EN_US,
                LocalizedRecipe.builder()
                        .name("Just vodka")
                        .description("Pour vodka into the glass")
                        .localizedId(new LocalizedId(Locale.EN_US))
                        .recipe(recipe)
                        .build());

        localizedRecipeHashMap.put(Locale.PL,
                LocalizedRecipe.builder()
                        .name("Czysta wódka")
                        .description("Nalej wódkę do szklanki")
                        .localizedId(new LocalizedId(Locale.PL))
                        .recipe(recipe)
                        .build());
        recipe.setLocalizations(localizedRecipeHashMap);
        recipe.setIngredientsMap(Map.of(ingredientRepository.findById(1L).get(), 100d));

        recipeRepository.save(recipe);
    }

}
