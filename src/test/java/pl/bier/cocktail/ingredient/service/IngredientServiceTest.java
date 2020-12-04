package pl.bier.cocktail.ingredient.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.common.entity.LocalizedId;
import pl.bier.cocktail.common.service.LocaleProvider;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.controller.model.PostIngredientRequest;
import pl.bier.cocktail.ingredient.controller.model.Category;
import pl.bier.cocktail.ingredient.entity.Ingredient;
import pl.bier.cocktail.ingredient.entity.LocalizedIngredient;
import pl.bier.cocktail.ingredient.repository.IngredientRepository;
import pl.bier.cocktail.ingredient.repository.LocalizedIngredientRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class IngredientServiceTest {

    private final LocalizedIngredientRepository localizedIngredientRepository = Mockito.mock(LocalizedIngredientRepository.class);

    private final IngredientRepository ingredientRepository = Mockito.mock(IngredientRepository.class);

    private final LocaleProvider localeProvider = Mockito.mock(LocaleProvider.class);

    private final IngredientService ingredientService = new IngredientService(ingredientRepository,
            localizedIngredientRepository, localeProvider);

    @BeforeEach
    public void resetMocks() {
        Mockito.reset(localizedIngredientRepository, ingredientRepository, localeProvider);
    }

    @Test
    void findIngredientById_ingredientFound() {
        Mockito.when(ingredientRepository.findById(12L)).thenReturn(Optional.of(Ingredient.builder()
                .id(12L).localizations(Map.of(Locale.DEFAULT, LocalizedIngredient.builder()
                        .name("foo").description("boo").build())).build()));

        Optional<Ingredient> ingredient = ingredientService.findIngredientById(12L);

        assertThat(ingredient).isPresent();
        assertThat(ingredient.get().getLocalizations().get(Locale.DEFAULT).getName()).isEqualTo("foo");
        assertThat(ingredient.get().getLocalizations().get(Locale.DEFAULT).getDescription()).isEqualTo("boo");
    }

    @Test
    void findIngredientById_ingredientNotFound() {
        Mockito.when(ingredientRepository.findById(12L)).thenReturn(Optional.empty());

        Optional<Ingredient> ingredient = ingredientService.findIngredientById(12L);

        assertThat(ingredient).isEmpty();
    }

    @Test
    void findLocalizedIngredientById_fondInCorrectId() {
        Mockito.when(localizedIngredientRepository.findByLocalizedIdIdAndLocalizedIdLocale(15L, Locale.PL))
                .thenReturn(Optional.of(LocalizedIngredient.builder()
                        .name("fooName")
                        .localizedId(new LocalizedId(15L, Locale.PL))
                        .description("fooDesc").build()));
        Mockito.when(localeProvider.provide()).thenReturn(Locale.PL);

        Optional<IngredientDto> ingredientDto = ingredientService.findLocalizedIngredientById(15L);

        assertThat(ingredientDto).isPresent();
        assertThat(ingredientDto.get().getLocale()).isEqualTo(Locale.PL);
        assertThat(ingredientDto.get().getName()).isEqualTo("fooName");
        assertThat(ingredientDto.get().getDescription()).isEqualTo("fooDesc");
    }

    @Test
    void findLocalizedIngredientById_fondInDefaultIdOnly() {
        Mockito.when(localizedIngredientRepository.findByLocalizedIdIdAndLocalizedIdLocale(15L, Locale.DEFAULT))
                .thenReturn(Optional.of(LocalizedIngredient.builder()
                        .name("fooDefName")
                        .description("fooDesc")
                        .localizedId(new LocalizedId(15L, Locale.DEFAULT))
                        .build()));
        Mockito.when(localizedIngredientRepository.findByLocalizedIdIdAndLocalizedIdLocale(15L, Locale.PL))
                .thenReturn(Optional.empty());
        Mockito.when(localeProvider.provide()).thenReturn(Locale.PL);

        Optional<IngredientDto> ingredientDto = ingredientService.findLocalizedIngredientById(15L);

        assertThat(ingredientDto).isPresent();
        assertThat(ingredientDto.get().getLocale()).isEqualTo(Locale.DEFAULT);
        assertThat(ingredientDto.get().getName()).isEqualTo("fooDefName");
        assertThat(ingredientDto.get().getDescription()).isEqualTo("fooDesc");
    }

    @Test
    void findByNameContains() {
        Mockito.when(localizedIngredientRepository.findByNameContainingAndLocalizedIdLocale("oo", Locale.PL))
                .thenReturn(List.of(LocalizedIngredient.builder()
                                .name("fooDefName")
                                .description("fooDesc")
                                .localizedId(new LocalizedId(15L, Locale.PL))
                                .build(),
                        LocalizedIngredient.builder()
                                .name("boooBo")
                                .description("otherDes")
                                .localizedId(new LocalizedId(16L, Locale.PL))
                                .build()));
        Mockito.when(localeProvider.provide()).thenReturn(Locale.PL);

        List<IngredientDto> ingredientDtos = ingredientService.findByNameContains(Optional.of("oo"));

        assertThat(ingredientDtos).containsExactlyInAnyOrder(
                IngredientDto.builder().id(15L).name("fooDefName").description("fooDesc").locale(Locale.PL).build(),
                IngredientDto.builder().id(16L).name("boooBo").description("otherDes").locale(Locale.PL).build()
        );
    }

    @Test
    void findByNameInDefaultLanguage() {
        Mockito.when(localizedIngredientRepository.findByNameContainingAndLocalizedIdLocale("oo", Locale.DEFAULT))
                .thenReturn(List.of(LocalizedIngredient.builder()
                                .name("fooDefName")
                                .description("fooDesc")
                                .localizedId(new LocalizedId(15L, Locale.DEFAULT))
                                .build(),
                        LocalizedIngredient.builder()
                                .name("boooBo")
                                .description("otherDes")
                                .localizedId(new LocalizedId(16L, Locale.DEFAULT))
                                .build()));
        Mockito.when(localeProvider.provide()).thenReturn(Locale.PL);

        List<IngredientDto> ingredientDtos = ingredientService.findByNameInDefaultLanguage(Optional.of("oo"));

        assertThat(ingredientDtos).containsExactlyInAnyOrder(
                IngredientDto.builder().id(15L).name("fooDefName").description("fooDesc").locale(Locale.DEFAULT).build(),
                IngredientDto.builder().id(16L).name("boooBo").description("otherDes").locale(Locale.DEFAULT).build()
        );
    }

    @Test
    void saveIngredient() {
        Mockito.when(ingredientRepository.save(Mockito.any())).thenReturn(Ingredient.builder().id(45L).build());

        ingredientService.saveIngredient(PostIngredientRequest.builder()
                .category(Category.JUICE)
                .alcoholByVolumePerMil(0)
                .localizations(Map.of(
                        Locale.DEFAULT, PostIngredientRequest.Localization.builder()
                                .description("juice from apples")
                                .name("apple juice")
                                .build(),
                        Locale.PL, PostIngredientRequest.Localization.builder()
                                .description("sok z jabłek")
                                .name("sok jabłkowy")
                                .build()
                )).build());

        Mockito.verify(ingredientRepository, Mockito.times(1)).save(Ingredient.builder().category(Category.JUICE)
                .alcoholByVolumePerMil(0)
                .localizations(Map.of(
                        Locale.DEFAULT, LocalizedIngredient.builder()
                                .name("apple juice")
                                .description("juice from apples")
                                .localizedId(new LocalizedId(Locale.DEFAULT))
                                .build(),
                        Locale.PL, LocalizedIngredient.builder()
                                .name("sok jabłkowy")
                                .description("sok z jabłek")
                                .localizedId(new LocalizedId(Locale.PL))
                                .build()
                )).build());
    }

}
