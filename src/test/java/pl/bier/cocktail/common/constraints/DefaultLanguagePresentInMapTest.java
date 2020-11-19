package pl.bier.cocktail.common.constraints;

import org.junit.jupiter.api.Test;
import pl.bier.cocktail.common.entity.Locale;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultLanguagePresentInMapTest {

    @Test
    public void validate_correctList() {
        DefaultLanguagePresentInMap validator = new DefaultLanguagePresentInMap();

        boolean result = validator.isValid(Map.of(Locale.PL, "foo", Locale.DEFAULT, "boo"), null);

        assertThat(result).isTrue();
    }

    @Test
    public void validate_inCorrectList() {
        DefaultLanguagePresentInMap validator = new DefaultLanguagePresentInMap();

        boolean result = validator.isValid(Map.of(Locale.PL, "booo"), null);

        assertThat(result).isFalse();
    }

}
