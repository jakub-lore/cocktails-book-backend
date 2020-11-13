package pl.bier.cocktail.common.constraints;

import pl.bier.cocktail.common.entity.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Map;

public class DefaultLanguagePresentInCollection implements ConstraintValidator<DefaultLanguagePresent, Collection<Locale>> {

    @Override
    public void initialize(DefaultLanguagePresent constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<Locale> value, ConstraintValidatorContext context) {
        return value.contains(Locale.DEFAULT);
    }

}
