package pl.bier.cocktail.ingredient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.ingredient.repository.IngredientRepository;
import pl.bier.cocktail.ingredient.repository.LocalizedIngredientRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RequestScope
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    private final LocalizedIngredientRepository localizedIngredientRepository;

    private final HttpServletRequest request;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, LocalizedIngredientRepository localIngredientRepo,
                             HttpServletRequest request) {
        this.ingredientRepository = ingredientRepository;
        this.localizedIngredientRepository = localIngredientRepo;
        this.request = request;
    }

    public Optional<Ingredient> findIngredientById(long id) {
        return ingredientRepository.findById(id);
    }

    public Optional<LocalizedIngredient> findLocalizedIngredientById(long id) {
        System.out.println(request.getLocale());
        return localizedIngredientRepository.findByLocalizedIdIdAndLocalizedIdLocale(id, request.getLocale().toString());
    }


    public List<LocalizedIngredient> findByNameStart(String start){
        return localizedIngredientRepository.findByNameStartingWithAndLocalizedIdLocale(start, request.getLocale().toString());
    }

}
