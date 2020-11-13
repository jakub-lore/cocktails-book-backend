package pl.bier.cocktail.common.constraints;

import pl.bier.cocktail.common.entity.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class DefaultLanguagePresentInMap implements ConstraintValidator<DefaultLanguagePresent, Map<Locale, ?>> {

    @Override
    public void initialize(DefaultLanguagePresent constraintAnnotation) {
    }

    @Override
    public boolean isValid(Map<Locale, ?> value, ConstraintValidatorContext context) {
        return value.containsKey(Locale.DEFAULT);
    }

}
