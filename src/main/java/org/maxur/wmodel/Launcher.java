package org.maxur.wmodel;

import com.codahale.metrics.JmxReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.UrlConfigurationSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.h2.tools.RunScript;
import org.maxur.wmodel.service.UserService;
import org.maxur.wmodel.view.RuntimeExceptionHandler;
import org.maxur.wmodel.view.UserResource;
import org.maxur.wmodel.view.ValidationExceptionHandler;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.SQLException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Launcher extends Application<Launcher.AppConfiguration> {

    public static void main(String[] args) throws Exception {
        URL resource = Launcher.class.getClassLoader().getResource("wmodel.yml");
        if (resource == null) {
            return;
        }
        new Launcher().run("server", resource.toString());
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new UrlConfigurationSourceProvider());
    }

    @Override
    public void run(final AppConfiguration cfg, final Environment env) throws IOException, SQLException {
        DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        JmxReporter.forRegistry(env.metrics()).build().start();
        initDB(dbi);
        UserService service = new UserService(dbi);
        initRest(service, env.jersey());
    }

    private void initRest(UserService service, JerseyEnvironment jersey) {
        jersey.register(RuntimeExceptionHandler.class);
        jersey.register(ValidationExceptionHandler.class);
        jersey.register(new UserResource(service));
    }

    private void initDB(DBI dbi) throws IOException, SQLException {
        try (Handle h = dbi.open()) {
            runScript(h, "/db.ddl");
            runScript(h, "/test.dml");
        }
    }

    private void runScript(Handle h, String script) throws IOException, SQLException {
        try (
                InputStream is = getClass().getResourceAsStream(script);
                Reader reader = new InputStreamReader(is)
        ) {
            RunScript.execute(h.getConnection(), reader);
        }
    }

    public static class AppConfiguration extends Configuration {
        @Valid
        @NotNull
        @JsonProperty
        private DataSourceFactory database = new DataSourceFactory();

        public DataSourceFactory getDataSourceFactory() {
            return database;
        }
    }

}
