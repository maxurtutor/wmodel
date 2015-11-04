package org.maxur.wmodel;


import com.codahale.metrics.JmxReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.maxur.wmodel.da.GroupDAO;
import org.maxur.wmodel.da.UserDAO;
import org.maxur.wmodel.service.UserRepository;
import org.maxur.wmodel.view.UserResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static java.util.Arrays.stream;
import static org.maxur.wmodel.service.ServiceLocator.persist;

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


        persist(UserDAO.class, dbi.onDemand(UserDAO.class));
        persist(GroupDAO.class, dbi.onDemand(GroupDAO.class));

        try (Handle h = dbi.open()) {
            h.execute("create table t_group (group_id int primary key, name varchar(100))");
            h.execute("create table t_user (user_id int primary key auto_increment, name varchar(100), group_id int)");
            h.insert("insert into t_group (group_id, name) values (?, ?)", 1, "developers");
            String[] names = {"Ivanov", "Petrov", "Sidorov"};
            stream(names)
                    .forEach(name -> h.insert("insert into t_user (name, group_id) values (?, ?)", name, 1));
        }


        env.jersey().register(new UserResource(new UserRepository()));
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
