package org.maxur.wmodel;


import com.codahale.metrics.JmxReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.maxur.wmodel.view.UserResource;
import org.skife.jdbi.v2.DBI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    public void run(final AppConfiguration cfg, final Environment env) {
        DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        JmxReporter.forRegistry(env.metrics()).build().start();
        env.jersey().register(new UserResource(dbi));
    }

    public static class AppConfiguration extends Configuration {
        @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory();
        public DataSourceFactory getDataSourceFactory() {
            return database;
        }
    }
}
