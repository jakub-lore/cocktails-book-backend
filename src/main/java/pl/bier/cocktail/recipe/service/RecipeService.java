package pl.bier.cocktail.recipe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.common.service.LocaleProvider;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.recipe.controller.model.PostRecipeRequest;
import pl.bier.cocktail.recipe.controller.model.RecipeDto;
import pl.bier.cocktail.recipe.entity.LocalizedRecipe;
import pl.bier.cocktail.recipe.entity.Recipe;
import pl.bier.cocktail.recipe.repository.LocalizedRecipeRepository;
import pl.bier.cocktail.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository repository;

    private final LocalizedRecipeRepository localizedRepository;

    private final LocaleProvider localeProvider;

    @Autowired
    public RecipeService(RecipeRepository repository, LocalizedRecipeRepository localizedRepository,
                         LocaleProvider localeProvider) {
        this.repository = repository;
        this.localizedRepository = localizedRepository;
        this.localeProvider = localeProvider;
    }

    public Optional<RecipeDto> findRecipeById(Long id) {
        return localizedRepository.findByLocalizedIdIdAndLocalizedIdLocale(id, localeProvider.provide())
                .map(l -> RecipeDto.entityToDtoMapper().apply(l));
    }

    public List<RecipeDto> findByNameContains(Optional<String> fragmentOptional) {
        List<LocalizedRecipe> recipes = fragmentOptional.isEmpty()
                ? localizedRepository.findByLocalizedIdLocale(localeProvider.provide())
                : localizedRepository.findByNameContainingAndLocalizedIdLocale(fragmentOptional.get(),
                localeProvider.provide());

        return recipes.stream().map(e -> RecipeDto.entityToDtoMapper().apply(e)).collect(Collectors.toList());
    }

    public long saveRecipe(PostRecipeRequest request) {
        repository.save(createEntityFromRequest(request));
        return 1L;
    }

    private Recipe createEntityFromRequest(PostRecipeRequest request) {
        Recipe recipe = Recipe.builder()
                .build();
        recipe.setLocalizations(
                request.getLocalizations().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e -> LocalizedRecipe.builder()
                                        .recipe(recipe)
                                        .name(e.getValue().getName())
                                        .description(e.getValue().getDescription())
                                        .localizedId(new LocalizedId(e.getKey()))
                                        .build())));
        return recipe;
    }

}
