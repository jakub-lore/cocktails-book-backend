package pl.bier.cocktail.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.common.service.LocaleProvider;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.service.IngredientService;
import pl.bier.cocktail.recipe.controller.model.PostRecipeRequest;
import pl.bier.cocktail.recipe.controller.model.RecipeDto;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;
import pl.bier.cocktail.recipe.entity.Recipe;
import pl.bier.cocktail.recipe.repository.LocalizedRecipeRepository;
import pl.bier.cocktail.recipe.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    private final LocalizedRecipeRepository localizedRepository;

    private final LocaleProvider localeProvider;

    private final IngredientService ingredientService;

    @Autowired
    public RecipeService(RecipeRepository repository, LocalizedRecipeRepository localizedRepository,
                         LocaleProvider localeProvider, IngredientService ingredientService) {
        this.repository = repository;
        this.localizedRepository = localizedRepository;
        this.localeProvider = localeProvider;
        this.ingredientService = ingredientService;
    }

    public Optional<RecipeDto> findLocalizedRecipeById(Long id) {
        return localizedRepository.findByLocalizedIdIdAndLocalizedIdLocale(id, localeProvider.provide())
                .map(this::mapToDto);
    }

    public List<RecipeDto> findByNameContains(Optional<String> fragmentOptional) {
        List<LocalizedRecipe> recipes = fragmentOptional.isEmpty()
                ? localizedRepository.findByLocalizedIdLocale(localeProvider.provide())
                : localizedRepository.findByNameContainingAndLocalizedIdLocale(fragmentOptional.get(),
                localeProvider.provide());

        return recipes.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public long saveRecipe(PostRecipeRequest request) {
        return repository.save(createEntityFromRequest(request)).getId();
    }

    private Recipe createEntityFromRequest(PostRecipeRequest request) {
        Recipe recipe = Recipe.builder()
                .ingredientsMap(new HashMap<>())
                .localizations(new HashMap<>())
                .build();
        request.getLocalizations().forEach((k, v) -> {
            recipe.getLocalizations().put(k,
                    LocalizedRecipe.builder()
                            .description(v.getDescription())
                            .name(v.getName())
                            .localizedId(new LocalizedId(k))
                            .recipe(recipe)
                            .build());
        });
        request.getIngredients().forEach((k, v) -> {
            recipe.getIngredientsMap().put(ingredientService.findIngredientById(k).orElseThrow(), v);
        });

        return recipe;
    }

    private RecipeDto mapToDto(LocalizedRecipe localizedRecipe) {
        RecipeDto recipeDto = RecipeDto.builder()
                .description(localizedRecipe.getDescription())
                .name(localizedRecipe.getName())
                .locale(localizedRecipe.getLocalizedId().getLocale())
                .id(localizedRecipe.getLocalizedId().getId())
                .ingredients(new HashMap<>())
                .build();
        localizedRecipe.getRecipe().getIngredientsMap().forEach((k, v) -> {
            recipeDto.getIngredients()
                    .put(k.getId(), v);
        });
        return recipeDto;
    }

}
