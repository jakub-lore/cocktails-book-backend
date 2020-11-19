package pl.bier.cocktail.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pl.bier.cocktail.common.entity.Locale;

import javax.servlet.http.HttpServletRequest;

@Service
public class LocaleProvider {

    private final HttpServletRequest request;

    @Autowired
    public LocaleProvider(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                  HttpServletRequest request) {
        this.request = request;
    }

    public Locale provide() {
        return Locale.from(request.getHeader(HttpHeaders.ACCEPT_LANGUAGE)).orElse(Locale.DEFAULT);
    }

}
