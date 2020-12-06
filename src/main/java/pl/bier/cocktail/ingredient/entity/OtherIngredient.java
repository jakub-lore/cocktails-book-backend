package pl.bier.cocktail.ingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Setter
//@Getter
//@EqualsAndHashCode(callSuper = true)
//@ToString
public class OtherIngredient extends Ingredient {

    private String defaultPieceName;
}
