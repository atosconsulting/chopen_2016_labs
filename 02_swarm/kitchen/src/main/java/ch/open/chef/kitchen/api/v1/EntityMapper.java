package ch.open.chef.kitchen.api.v1;

import ch.open.chef.kitchen.menu.Ingredient;
import ch.open.chef.kitchen.menu.Menu;
import ch.open.chef.kitchen.menu.Recipe;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EntityMapper {

    public IngredientDto fromIngredient(Ingredient ingredient) {
        return IngredientDto.builder()
                .name(ingredient.getName())
                .measure(ingredient.getMeasure())
                .unit(ingredient.getUnit())
                .build();
    }

    public List<IngredientDto> fromIngredients(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(EntityMapper::fromIngredient)
                .collect(Collectors.toList());
    }

    public RecipeDto fromRecipe(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .ingredients(fromIngredients(recipe.getIngredients()))
                .build();
    }

    public List<RecipeDto> fromRecipes(List<Recipe> recipies) {
        return recipies.stream()
                .map(EntityMapper::fromRecipe)
                .collect(Collectors.toList());
    }

    public List<MenuDto> fromMenus(List<Menu> menus) {
        return menus.stream()
                .map(EntityMapper::fromMenu)
                .collect(Collectors.toList());
    }

    public MenuDto fromMenu(Menu menu) {
        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .ingredientIds(
                        menu.getRecipes().stream()
                            .map(r -> r.getIngredients())
                            .flatMap(i -> i.stream())
                            .filter(i -> i.getStockId() != null)
                            .map(Ingredient::getStockId)
                            .collect(Collectors.toList())
                )
                .build();
    }
}
