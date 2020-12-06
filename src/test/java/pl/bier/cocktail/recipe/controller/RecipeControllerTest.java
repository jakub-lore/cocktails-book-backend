package pl.bier.cocktail.recipe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.bier.cocktail.base.AbstractRestControllerTest;
import pl.bier.cocktail.common.entity.Locale;
import pl.bier.cocktail.recipe.controller.model.GetMultipleRecipeResponse;
import pl.bier.cocktail.recipe.controller.model.GetRecipeResponse;
import pl.bier.cocktail.recipe.controller.model.PostRecipeRequest;
import pl.bier.cocktail.recipe.controller.model.RecipeDto;
import pl.bier.cocktail.recipe.service.RecipeService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void findRecipeById_recipeNotExisting() throws Exception {
        when(service.findLocalizedRecipeById(15L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/recipe/15"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void findRecipeByFragment() throws Exception {
        when(service.findByNameContains(Optional.of("fo")))
                .thenReturn(List.of(sampleRecipe()));

        mockMvc.perform(get("/recipes?fragment=foo"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(mapToJson(GetMultipleRecipeResponse.builder()
                        .recipes(List.of(sampleRecipe()))
                        .build())));
    }

    @Test
    void postRecipe_correctRequest() throws Exception {
        when(service.saveRecipe(any())).thenReturn(123L);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(correctPostRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string("123"));

        verify(service, times(1)).saveRecipe(correctPostRequest());
    }

    @Test
    void postRecipe_badRequest() throws Exception {
        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(badPostRequest())))
                .andExpect(status().isBadRequest());

        verify(service, never()).saveRecipe(any());
    }

    private RecipeDto sampleRecipe() {
        return RecipeDto.builder()
                .id(17L)
                .name("name foo")
                .description("recipe decs")
                .ingredients(Map.of(1L, 13d, 12L, 0.5d))
                .locale(Locale.DEFAULT)
                .build();
    }

    private PostRecipeRequest correctPostRequest() {
        return PostRecipeRequest.builder()
                .ingredients(Map.of(1L, 20d))
                .localizations(Map.of(Locale.DEFAULT, PostRecipeRequest.Localization.builder()
                        .name("example name")
                        .description("ex description")
                        .build()))
                .build();
    }

    private PostRecipeRequest badPostRequest() {
        return PostRecipeRequest.builder()
                .ingredients(Map.of(1L, 20d))
                .localizations(Map.of(Locale.PL, PostRecipeRequest.Localization.builder()
                        .name("example name")
                        .description("ex description")
                        .build()))
                .build();
    }

}
