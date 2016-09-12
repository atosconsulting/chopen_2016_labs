package ch.open.chef.kitchen.menu;

import static javax.persistence.GenerationType.AUTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({
    @NamedQuery(name = Recipe.FIND_ALL, query = "from Recipe")
})
@Getter @Setter
public class Recipe {

    public static final String FIND_ALL = "Recipe.findAll";

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    private String title;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

}
