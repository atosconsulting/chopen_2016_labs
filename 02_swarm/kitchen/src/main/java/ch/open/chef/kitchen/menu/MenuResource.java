package ch.open.chef.kitchen.menu;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import ch.open.chef.kitchen.api.v1.EntityMapper;
import ch.open.chef.kitchen.api.v1.MenuDto;
import ch.open.chef.kitchen.api.v1.MenuOrderDto;
import io.swagger.annotations.*;

@Path("/v1/menus")
@Api("menus")
@ApplicationScoped
public class MenuResource {

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces("application/json")
    @ApiOperation(value = "Get the todays menus",
        notes = "Returns a list of menues which the kitchen can produce today.",
        produces = "application/json"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Menus successfully retrieved",
                response = MenuDto.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "Internal error")
    })
    public Response list() {
        List<Menu> menus = entityManager.createNamedQuery(Menu.FIND_ALL_WITH_RECIPES, Menu.class).getResultList();
        return Response.ok().entity(EntityMapper.fromMenus(menus)).build();
    }

    @POST
    @ApiOperation(value = "Request menus to be cooked")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Menu orders successfully placed"),
        @ApiResponse(code = 500, message = "Internal error")
    })
    @Consumes("application/json")
    public Response order(List<MenuOrderDto> menuOrders) {
        menuOrders.stream().forEach(System.out::println);
        // TODO: Inventory checkout. No inventory available yet.
        return Response.ok(menuOrders).build();
    }


}
