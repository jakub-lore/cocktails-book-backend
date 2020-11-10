package pl.bier.cocktail.ingredient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bier.cocktail.ingredient.controller.model.GetIngredientResponse;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.controller.model.GetMultipleIngredientsResponse;
import pl.bier.cocktail.ingredient.service.IngredientService;

import java.util.stream.Collectors;

@RestController
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredient")
    public GetIngredientResponse findIngredientById(long id) {
        return ingredientService.findLocalizedIngredientById(id)
                .map(local -> GetIngredientResponse.builder()
                        .ingredient(IngredientDto.entityToDtoMapper().apply(local))
                        .build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search/ingredient")
    public GetMultipleIngredientsResponse findIngredient(String start) {
        return GetMultipleIngredientsResponse.builder()
                .ingredients(ingredientService
                        .findByNameStart(start)
                        .stream()
                        .map(local -> IngredientDto.entityToDtoMapper().apply(local))
                        .collect(Collectors.toList()))
                        .build();
    }

}
