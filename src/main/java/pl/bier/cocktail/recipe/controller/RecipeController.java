package pl.bier.cocktail.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bier.cocktail.recipe.controller.model.GetMultipleRecipeResponse;
import pl.bier.cocktail.recipe.controller.model.GetRecipeResponse;
import pl.bier.cocktail.recipe.controller.model.PostRecipeRequest;
import pl.bier.cocktail.recipe.service.RecipeService;

import java.util.Optional;

@RestController
public class RecipeController {

    private final RecipeService service;

    @Autowired
    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/recipe/{id}")
    public GetRecipeResponse findRecipeById(@PathVariable Long id) {
        return service.findLocalizedRecipeById(id)
                .map(r -> GetRecipeResponse.builder().recipe(r).build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/recipes")
    public GetMultipleRecipeResponse findRecipeByFragment(Optional<String> fragment) {
        return GetMultipleRecipeResponse.builder()
                .recipes(service.findByNameContains(fragment))
                .build();
    }

    @PostMapping("/recipes")
    public long postRecipe(@RequestBody PostRecipeRequest recipeRequest) {
        return service.saveRecipe(recipeRequest);
    }

}
