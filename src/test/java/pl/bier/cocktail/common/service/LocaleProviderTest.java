package pl.bier.cocktail.common.service;

import org.apache.tomcat.util.http.parser.AcceptLanguage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import pl.bier.cocktail.common.entity.Locale;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LocaleProviderTest {

    @Test
    public void provideLocale_localePresent() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn("pl-PL");
        LocaleProvider provider = new LocaleProvider(request);

        Locale result = provider.provide();

        assertThat(result).isEqualTo(Locale.PL);
    }

    @Test
    public void provideLocale_noLocaleHeader() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn(null);
        LocaleProvider provider = new LocaleProvider(request);

        Locale result = provider.provide();

        assertThat(result).isEqualTo(Locale.DEFAULT);
    }

    @Test
    public void provideLocale_unsupportedLanguage() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).thenReturn("bo-FO");
        LocaleProvider provider = new LocaleProvider(request);

        Locale result = provider.provide();

        assertThat(result).isEqualTo(Locale.DEFAULT);
    }

}
