package ch.open.chef.kitchen.menu;

import static javax.persistence.GenerationType.AUTO;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@NamedQueries({
    @NamedQuery(name = Menu.FIND_ALL_WITH_RECIPES, query = "select distinct m from Menu m join fetch m.recipes")
})
@Getter @Setter
public class Menu {

    public static final String FIND_ALL_WITH_RECIPES = "Menu.findAllWithRecipes";

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @ManyToMany
    private List<Recipe> recipes;

}
