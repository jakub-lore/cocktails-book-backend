package pl.bier.cocktail.common.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class LocalizedId implements Serializable {

    private static final long serialVersionUID = 54789427190824L;

    private Long id;

    @Enumerated(EnumType.STRING)
    private Locale locale;

    public LocalizedId(Locale locale) {
        this.locale = locale;
    }

}
