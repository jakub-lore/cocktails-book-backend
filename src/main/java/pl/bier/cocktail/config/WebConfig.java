package pl.bier.cocktail.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.bier.cocktail.common.entity.Locale;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(new LocaleConverter());
    }

    public static class LocaleConverter implements Converter<String, Locale> {
        @Override
        public Locale convert(String source) {
            return Arrays.stream(Locale.values()).filter(l -> l.getHeaderValue().equalsIgnoreCase(source))
                    .findFirst().orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        }
    }

}
