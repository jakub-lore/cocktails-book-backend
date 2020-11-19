package pl.bier.cocktail.ingredient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.common.service.LocaleProvider;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.controller.model.PostIngredientRequest;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.ingredient.repository.IngredientRepository;
import pl.bier.cocktail.ingredient.repository.LocalizedIngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository repository;

    private final LocalizedIngredientRepository localizedRepository;

    private final LocaleProvider localeProvider;

    @Autowired
    public IngredientService(IngredientRepository repository, LocalizedIngredientRepository localIngredientRepo,
                             LocaleProvider localeProvider) {
        this.repository = repository;
        this.localizedRepository = localIngredientRepo;
        this.localeProvider = localeProvider;
    }

    public Optional<Ingredient> findIngredientById(long id) {
        return repository.findById(id);
    }

    public Optional<IngredientDto> findLocalizedIngredientById(long id) {
        return localizedRepository
                .findByLocalizedIdIdAndLocalizedIdLocale(id, localeProvider.provide())
                .or(() -> localizedRepository.findByLocalizedIdIdAndLocalizedIdLocale(id, Locale.DEFAULT))
                .map(e -> IngredientDto.entityToDtoMapper().apply(e));
    }

    public List<IngredientDto> findByNameContains(Optional<String> fragmentOptional) {
        List<LocalizedIngredient> ingredients = fragmentOptional.isEmpty()
                ? localizedRepository.findByLocalizedIdLocale(localeProvider.provide())
                : localizedRepository.findByNameContainingAndLocalizedIdLocale(fragmentOptional.get(),
                localeProvider.provide());

        return ingredients.stream().map(e -> IngredientDto.entityToDtoMapper().apply(e)).collect(Collectors.toList());
    }

    public List<IngredientDto> findByNameInDefaultLanguage(Optional<String> fragmentOptional) {
        List<LocalizedIngredient> ingredients = fragmentOptional.isEmpty()
                ? localizedRepository.findByLocalizedIdLocale(Locale.DEFAULT)
                : localizedRepository.findByNameContainingAndLocalizedIdLocale(fragmentOptional.get(), Locale.DEFAULT);

        return ingredients.stream().map(e -> IngredientDto.entityToDtoMapper().apply(e)).collect(Collectors.toList());
    }

    public long saveIngredient(PostIngredientRequest request) {
        Ingredient ingredient = PostIngredientRequest.requestToEntityMapper().apply(request);
        repository.save(ingredient);
        return ingredient.getId();
    }


}
