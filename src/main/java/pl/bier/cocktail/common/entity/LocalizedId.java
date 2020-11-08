package pl.bier.cocktail.common.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class LocalizedId implements Serializable {

    private static final long serialVersionUID = 54789427190824L;

    private Long id;

    private String locale;

    public LocalizedId(String locale) {
        this.locale = locale;
    }

}
