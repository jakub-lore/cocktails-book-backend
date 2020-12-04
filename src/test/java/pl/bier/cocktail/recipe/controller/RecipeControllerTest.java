package pl.bier.cocktail.recipe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.bier.cocktail.base.AbstractRestControllerTest;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.ingredient.controller.model.GetIngredientResponse;
import pl.bier.cocktail.recipe.controller.model.GetRecipeResponse;
import pl.bier.cocktail.recipe.controller.model.RecipeDto;
import pl.bier.cocktail.recipe.entity.Recipe;
import pl.bier.cocktail.recipe.service.RecipeService;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest extends AbstractRestControllerTest {

    @MockBean
    private RecipeService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void reset() {
        Mockito.reset(service);
    }

    @Test
    void findRecipeById_recipeExists() throws Exception {
        when(service.findLocalizedRecipeById(15L))
                .thenReturn(Optional.of(sampleRecipe()));

        mockMvc.perform(get("/recipe/15"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(
                        mapToJson(GetRecipeResponse.builder()
                                .recipe(sampleRecipe())
                                .build())
                ));
    }

    @Test
    void findRecipeByFragment() {
    }

    @Test
    void postRecipe() {
    }

    private RecipeDto sampleRecipe() {
        return RecipeDto.builder()
                .id(17L)
                .name("name foo")
                .description("recip decs")
                .ingredients(Map.of(1L, 13d, 12L, 0.5d))
                .locale(Locale.DEFAULT)
                .build();
    }

}
