package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;


/**
 * @author myunusov
 * @version 1.0
 * @since <pre>12.11.2015</pre>
 */ // The actual service
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final DBI dbi;

    public UserResource(DBI dbi) {
        this.dbi = dbi;
    }

    @Timed
    @POST
    @Path("/user")
    public Response add(User user) {
        final Integer count = userDAO().findCountUsersByGroup(user.groupId);
        if (count == 5) {
            return status(BAD_REQUEST)
                    .entity("More users than allowed in group")
                    .type("text/plain")
                    .build();
        }
        return ok(find(userDAO().insert(user.name, user.groupId)))
                .build();
    }

    @Timed
    @GET
    @Path("/user/{id}")
    public User find(@PathParam("id") Integer userId) {
        final User user = userDAO().findById(userId);
        user.groupName = groupDAO().findById(user.groupId).name;
        return user;
    }

    @Timed
    @GET
    @Path("/users")
    public List<User> all() {
        final List<User> users = userDAO().findAll();
        users.stream().forEach(u -> u.groupName = groupDAO().findById(u.groupId).name);
        return users;
    }

    private UserDAO userDAO() {
        return dbi.onDemand(UserDAO.class);
    }

    private GroupDAO groupDAO() {
        return dbi.onDemand(GroupDAO.class);
    }
}
