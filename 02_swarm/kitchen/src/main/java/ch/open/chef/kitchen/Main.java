package ch.open.chef.kitchen;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.logging.Level;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.keycloak.Secured;
import org.wildfly.swarm.logging.LoggingFraction;
import org.wildfly.swarm.spi.api.StageConfig;
import org.wildfly.swarm.swagger.SwaggerArchive;
import org.wildfly.swarm.undertow.WARArchive;

public class Main {

    public static void main(String[] args) throws Exception {
        final Swarm swarm = new Swarm().withStageConfig(Main.class.getResource("/project-stages.yml"));
        final StageConfig config = swarm.stageConfig();

        swarm.fraction(
            new DatasourcesFraction()
                .dataSource("KitchenDS", (ds) -> {
                    ds.driverName("postgresql");
                    ds.connectionUrl(
                        config.resolve("database.connection.url").getValue()
                    );
                    ds.userName(config.resolve("database.connection.user").getValue());
                    ds.password(config.resolve("database.connection.password").getValue());
                })
            )
            .fraction(new JPAFraction()
                .defaultDatasource("jboss/datasources/KitchenDS")
            );

        final JAXRSArchive deployment =
            ShrinkWrap.create(SwaggerArchive.class, "kitchen-rest.war")
                .setResourcePackages("ch.open.chef.kitchen")
                .setVersion("1.0.0")
                .setDescription("This is the Kitchen API")
                .setPrettyPrint(true)
            .as(JAXRSArchive.class)
                .addPackages(true, Main.class.getPackage())
                .addAsWebInfResource(new ClassLoaderAsset("META-INF/persistence.xml", Main.class.getClassLoader()), "classes/META-INF/persistence.xml")
                .addAsWebInfResource(new ClassLoaderAsset("META-INF/load.sql", Main.class.getClassLoader()), "classes/META-INF/load.sql")
                .addAllDependencies();

        final WARArchive docs = ShrinkWrap.create(WARArchive.class, "kitchen-docs.war")
            .setContextRoot("/docs")
            .addAsWebResource(new ClassLoaderAsset("index.html", Main.class.getClassLoader()), "index.html");

        swarm.start()
            .fraction(LoggingFraction.createDefaultLoggingFraction(Level.INFO))
            .deploy(deployment)
            .deploy(docs);
    }

}
