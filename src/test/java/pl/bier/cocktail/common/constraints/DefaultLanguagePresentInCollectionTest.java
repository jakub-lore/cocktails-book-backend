package pl.bier.cocktail.common.constraints;

import org.junit.jupiter.api.Test;
import pl.bier.cocktail.common.entity.Locale;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLanguagePresentInCollectionTest {

    @Test
    public void validate_correctList() {
        DefaultLanguagePresentInCollection validator = new DefaultLanguagePresentInCollection();

        boolean result = validator.isValid(List.of(Locale.PL, Locale.DEFAULT), null);

        assertThat(result).isTrue();
    }

    @Test
    public void validate_inCorrectList() {
        DefaultLanguagePresentInCollection validator = new DefaultLanguagePresentInCollection();

        boolean result = validator.isValid(List.of(Locale.PL), null);

        assertThat(result).isFalse();
    }

}
