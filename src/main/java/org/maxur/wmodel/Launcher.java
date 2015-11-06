package org.maxur.wmodel;


import com.codahale.metrics.JmxReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.h2.tools.RunScript;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.service.UserService;
import org.maxur.wmodel.view.RuntimeExceptionHandler;
import org.maxur.wmodel.view.ValidationExceptionHandler;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;

import static java.util.Arrays.asList;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Launcher extends Application<Launcher.AppConfiguration> {

    public static void main(final String[] args) throws Exception {
        new Launcher().run("server", "./src/main/resources/wmodel.yml");
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(final AppConfiguration cfg, final Environment env) throws IOException, SQLException {
        DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        JmxReporter.forRegistry(env.metrics()).build().start();

        try (Handle h = dbi.open()) {
            try (InputStream is = getClass().getResourceAsStream("/db.ddl");
                 Reader reader = new InputStreamReader(is)
            ) {
                RunScript.execute(h.getConnection(), reader);
            }

            asList("developers", "managers")
                    .stream()
                    .forEach(name -> h.insert("INSERT INTO t_group (name) VALUES (?)", name));

            asList("Ivanov", "Petrov", "Sidorov")
                    .stream()
                    .forEach(name -> h.insert("INSERT INTO t_user (name, group_id) VALUES (?, ?)", name, 1));

        }

        final UserService service = new UserService(dbi.onDemand(UserDAO.class), dbi.onDemand(GroupDAO.class));
        env.jersey().register(RuntimeExceptionHandler.class);
        env.jersey().register(ValidationExceptionHandler.class);
        env.jersey().packages(getClass().getPackage().getName());
        env.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(env.lifecycle()).to(LifecycleEnvironment.class);
                bind(service).to(UserService.class);
            }
        });
    }

    public static class AppConfiguration extends Configuration {
        @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory();
        public DataSourceFactory getDataSourceFactory() {
            return database;
        }
    }
}
