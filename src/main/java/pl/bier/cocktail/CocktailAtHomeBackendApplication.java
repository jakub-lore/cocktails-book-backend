package pl.bier.cocktail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.ingredient.entity.Category;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.ingredient.repository.model.IngredientRepository;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootApplication
public class CocktailAtHomeBackendApplication implements CommandLineRunner {

    @Autowired
    private IngredientRepository ingredientRepository;

    public static void main(String[] args) {
        SpringApplication.run(CocktailAtHomeBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
		saveFirstIngredient();

		ingredientRepository.findAll().forEach(System.out::println);
	}

	private void saveFirstIngredient() {
		Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
				.category(Category.ALCOHOLIC_BEVERAGE)
				.build());
		HashMap<String, LocalizedIngredient> localizedIngredientHashMap = new HashMap<>();
		localizedIngredientHashMap.put("en", LocalizedIngredient.builder().name("rum").localizedId(new LocalizedId("en")).ingredient(ingredient).build());
		ingredient.setLocalizations(localizedIngredientHashMap);
		ingredientRepository.save(ingredient);
	}

}
