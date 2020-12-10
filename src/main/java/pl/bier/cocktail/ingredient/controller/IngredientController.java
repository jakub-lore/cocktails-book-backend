package pl.bier.cocktail.ingredient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bier.cocktail.ingredient.controller.model.GetIngredientResponse;
import pl.bier.cocktail.ingredient.controller.model.GetMultipleIngredientsResponse;
import pl.bier.cocktail.ingredient.controller.model.PostIngredientRequest;
import pl.bier.cocktail.ingredient.service.IngredientService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredient/{id}")
    public GetIngredientResponse findIngredientById(@PathVariable Long id) {
        return ingredientService.findLocalizedIngredientById(id)
                .map(dto -> GetIngredientResponse.builder()
                        .ingredient(dto)
                        .build())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/ingredients")
    public GetMultipleIngredientsResponse findIngredient(Optional<String> fragment) {
        return GetMultipleIngredientsResponse.builder()
                .ingredients(ingredientService.findByNameContains(fragment))
                .build();
    }

    @PostMapping(value = "/ingredient")
    @ResponseStatus(HttpStatus.CREATED)
    public long PostIngredient(@RequestBody @Valid PostIngredientRequest request) {
        System.out.println(request);
        return ingredientService.saveIngredient(request);
    }

}
