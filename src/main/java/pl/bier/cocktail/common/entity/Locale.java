package pl.bier.cocktail.common.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum Locale {

    PL("pl-PL"),

    EN_US("en-US");

    private String headerValue;

    public static final Locale DEFAULT = Locale.EN_US;

    public static Optional<Locale> from(String header) {
        return Arrays.stream(Locale.values()).filter(l -> l.headerValue.equalsIgnoreCase(header)).findFirst();
    }

    @JsonValue
    public String getHeaderValue() {
        return headerValue;
    }
}
