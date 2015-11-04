package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.da.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */ // The actual service
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO dao;

    public UserResource(final DBI dbi) {
        this.dao = dbi.onDemand(UserDAO.class);

        try (Handle h = dbi.open()) {
            h.execute("create table user (id int primary key auto_increment, name varchar(100))");
            String[] names = {"Ivanov", "Petrov", "Sidorov"};
            stream(names)
                    .forEach(name -> h.insert("insert into user (name) values (?)", name));
        }
    }

    @Timed
    @POST
    @Path("/add")
    public User add(String name) {
        return find(dao.insert(name));
    }

    @Timed
    @GET
    @Path("/item/{id}")
    public User find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @Timed
    @GET
    @Path("/all")
    public List<User> all(@PathParam("id") Integer id) {
        return dao.all();
    }


}
