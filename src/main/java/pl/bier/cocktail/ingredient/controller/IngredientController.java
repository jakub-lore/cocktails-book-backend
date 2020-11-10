package pl.bier.cocktail.ingredient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.bier.cocktail.ingredient.controller.model.GetIngredientResponse;
import pl.bier.cocktail.ingredient.service.IngredientService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredient")
    public GetIngredientResponse findIngredient(long id) {
        System.out.println(ingredientService.findLocalizedIngredientById(id));
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
