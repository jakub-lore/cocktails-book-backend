package pl.bier.cocktail.ingredient.controller;

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
import pl.bier.cocktail.ingredient.controller.model.GetIngredientResponse;
import pl.bier.cocktail.ingredient.controller.model.GetMultipleIngredientsResponse;
import pl.bier.cocktail.ingredient.controller.model.IngredientDto;
import pl.bier.cocktail.ingredient.controller.model.PostIngredientRequest;
import pl.bier.cocktail.ingredient.entity.Category;
import pl.bier.cocktail.ingredient.service.IngredientService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest extends AbstractRestControllerTest {

    @MockBean
    private IngredientService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void reset() {
        Mockito.reset(service);
    }

    @Test
    void findIngredientById_correctId() throws Exception {
        when(service.findLocalizedIngredientById(15L))
                .thenReturn(Optional.of(sampleIngredient()));

        mockMvc.perform(get("/ingredient/15"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(
                        mapToJson(GetIngredientResponse.builder()
                                .ingredient(sampleIngredient()))
                ));
    }

    @Test
    void findIngredientById_inCorrectId() throws Exception {
        when(service.findLocalizedIngredientById(15L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/ingredient/15"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findIngredient() throws Exception {
        when(service.findByNameContains(Optional.of("name")))
                .thenReturn(List.of(sampleIngredient(), otherIngredient()));

        mockMvc.perform(get("/ingredients?fragment=name"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapToJson(GetMultipleIngredientsResponse.builder()
                                .ingredients(List.of(sampleIngredient(), otherIngredient()))
                                .build())
                ));
    }

    @Test
    void postIngredient_correctBody() throws Exception {
        when(service.saveIngredient(any())).thenReturn(19L);
        String postJson = "{\n" +
                "    \"category\": \"JUICE\",\n" +
                "    \"localizations\": {\n" +
                "        \"pl-PL\": {\n" +
                "            \"name\": \"sok jabłkowy\",\n" +
                "            \"description\": \"sok z jabłek\"\n" +
                "        },\n" +
                "        \"en-US\": {\n" +
                "            \"name\": \"apple juice\",\n" +
                "            \"description\": \"juice made of apples\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        mockMvc.perform(post("/ingredient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("19"));

        verify(service, times(1)).saveIngredient(argThat(r -> {
            return r.getCategory().equals(Category.JUICE) &&
                    r.getLocalizations().get(Locale.PL).getName().equals("sok jabłkowy") &&
                    r.getLocalizations().get(Locale.PL).getDescription().equals("sok z jabłek") &&
                    r.getLocalizations().get(Locale.EN_US).getName().equals("apple juice") &&
                    r.getLocalizations().get(Locale.EN_US).getDescription().equals("juice made of apples");

        }));
    }

    @Test
    void postIngredient_inCorrectBody() throws Exception {
        when(service.saveIngredient(any())).thenReturn(19L);
        String postJson = "{\n" +
                "    \"category\": \"JUICE\",\n" +
                "    \"localizations\": {\n" +
                "        \"pl-PL\": {\n" +
                "            \"name\": \"sok jabłkowy\",\n" +
                "            \"description\": \"sok z jabłek\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        mockMvc.perform(post("/ingredient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).saveIngredient(any());
    }


    private IngredientDto sampleIngredient() {
        return IngredientDto.builder()
                .name("example name")
                .description("Any description.")
                .id(15L)
                .locale(Locale.EN_US)
                .build();
    }

    private IngredientDto otherIngredient() {
        return IngredientDto.builder()
                .name("other name")
                .description("Something longer as description.")
                .id(15L)
                .locale(Locale.EN_US)
                .build();
    }

}
