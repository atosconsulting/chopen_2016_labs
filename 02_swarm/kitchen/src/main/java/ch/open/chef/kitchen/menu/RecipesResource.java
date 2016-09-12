package ch.open.chef.kitchen.menu;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ch.open.chef.kitchen.api.v1.EntityMapper;
import ch.open.chef.kitchen.api.v1.RecipeDto;
import io.swagger.annotations.*;

@Path("/v1/recipes")
@Api("recipes")
@ApplicationScoped
public class RecipesResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get all recipes",
        notes = "Returns a list of recipes",
        produces = "application/json"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Recipies successfully retrieved",
                response = RecipeDto.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal error")
    })
    public Response list() {
        List<Recipe> recipies = entityManager.createNamedQuery(Recipe.FIND_ALL, Recipe.class).getResultList();
        return Response.ok().entity(EntityMapper.fromRecipes(recipies)).build();
    }

}
