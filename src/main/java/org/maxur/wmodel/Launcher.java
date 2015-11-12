package org.maxur.wmodel;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.configuration.UrlConfigurationSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;

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
    public void run(final AppConfiguration cfg, final Environment env) {
        DBI dbi = new DBIFactory().build(env, cfg.getDataSourceFactory(), "db");
        JmxReporter.forRegistry(env.metrics()).build().start();
        env.jersey().register(new UserResource(dbi));
    }

    // The actual service
    @Path("/api")
    @Produces(MediaType.APPLICATION_JSON)
    public static class UserResource {

        private final DBI dbi;

        public UserResource(DBI dbi) {
            this.dbi = dbi;
            try (Handle h = dbi.open()) {
                h.execute("CREATE TABLE t_user (user_id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100))");
                String[] names = {"Ivanov", "Petrov", "Sidorov"};
                stream(names)
                        .forEach(name -> h.insert("INSERT INTO t_user (name) VALUES (?)", name));
            }
        }

        @Timed
        @POST
        @Path("/user")
        public Map<String, Object> add(String name) {
            try (Handle h = dbi.open()) {
                int id = h.createStatement("insert into t_user (name) values (:name)").bind("name", name)
                        .executeAndReturnGeneratedKeys(IntegerMapper.FIRST).first();
                return find(id);
            }
        }

        @Timed
        @GET
        @Path("/user/{id}")
        public Map<String, Object> find(@PathParam("id") Integer id) {
            try (Handle h = dbi.open()) {
                return h.createQuery("select user_id, name from t_user where user_id = :id").bind("id", id).first();
            }
        }

        @Timed
        @GET
        @Path("/users")
        public List<Map<String, Object>> all() {
            try (Handle h = dbi.open()) {
                return h.createQuery("select * from t_user").list();
            }
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
