package org.maxur.wmodel;


import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.IntegerMapper;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;

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

    // The actual service
    @Path("/api/user")
    @Produces(MediaType.APPLICATION_JSON)
    public static class UserResource {

        private final DBI dbi;

        public UserResource(DBI dbi) {
            this.dbi = dbi;
            try (Handle h = dbi.open()) {
                h.execute("create table user (id int primary key auto_increment, name varchar(100))");
                String[] names = { "Ivanov", "Petrov", "Sidorov" };
                stream(names)
                        .forEach(name -> h.insert("insert into user (name) values (?)", name));
            }
        }

        @Timed
        @POST @Path("/add")
        public Map<String, Object> add(String name) {
            try (Handle h = dbi.open()) {
                int id = h.createStatement("insert into user (name) values (:name)").bind("name", name)
                        .executeAndReturnGeneratedKeys(IntegerMapper.FIRST).first();
                return find(id);
            }
        }

        @Timed
        @GET @Path("/item/{id}")
        public Map<String, Object> find(@PathParam("id") Integer id) {
            try (Handle h = dbi.open()) {
                return h.createQuery("select id, name from user where id = :id").bind("id", id).first();
            }
        }

        @Timed
        @GET @Path("/all")
        public List<Map<String, Object>> all(@PathParam("id") Integer id) {
            try (Handle h = dbi.open()) {
                return h.createQuery("select * from user").list();
            }
        }

    }

    public static class AppConfiguration extends Configuration {
        @Valid @NotNull @JsonProperty private DataSourceFactory database = new DataSourceFactory();
        public DataSourceFactory getDataSourceFactory() {
            return database;
        }
    }
}
