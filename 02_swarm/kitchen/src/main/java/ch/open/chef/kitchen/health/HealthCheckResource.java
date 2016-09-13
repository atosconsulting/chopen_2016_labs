package ch.open.chef.kitchen.health;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.wildfly.swarm.monitor.Health;
import org.wildfly.swarm.monitor.HealthStatus;

@Path("/kitchen")
public class HealthCheckResource {

    @PersistenceContext
    private EntityManager em;

    @GET
    @Path("/db")
    @Health(inheritSecurity = false)
    public HealthStatus checkDb() {
        try {
            return em.createNativeQuery("SELECT 1").getSingleResult() != null ? HealthStatus.up() : HealthStatus.down();
        } catch (Exception e) {
            return HealthStatus.down();
        }
    }

}
