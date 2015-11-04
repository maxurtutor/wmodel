package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.da.GroupDAO;
import org.maxur.wmodel.da.UserDAO;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.service.UserService;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.stream;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;

    public UserResource(final DBI dbi) {
        final UserDAO userDAO = dbi.onDemand(UserDAO.class);
        final GroupDAO groupDAO = dbi.onDemand(GroupDAO.class);
        this.service = new UserService(userDAO, groupDAO);

        try (Handle h = dbi.open()) {
            h.execute("create table t_group (group_id int primary key, name varchar(100))");
            h.execute("create table t_user (user_id int primary key auto_increment, name varchar(100), group_id int)");
            h.insert("insert into t_group (group_id, name) values (?, ?)", 1, "developers");
            String[] names = {"Ivanov", "Petrov", "Sidorov"};
            stream(names)
                    .forEach(name -> h.insert("insert into t_user (name, group_id) values (?, ?)", name, 1));
        }
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(User user) {
        try {
            return Response.ok(service.insert(user)).build();
        } catch (RuntimeException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage())
                    .type("text/plain")
                    .build();
        }
    }

    @Timed
    @GET
    @Path("/{userId}")
    public User find(@PathParam("userId") Integer userId) {
        return service.find(userId);
    }

    @Timed
    @GET
    @Path("/all")
    public List<User> all() {
        return service.findAll();
    }


}
