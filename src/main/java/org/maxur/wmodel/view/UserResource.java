package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;

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
 * @since <pre>04.11.2015</pre>
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;
    private final GroupDAO groupDAO;

    public UserResource(final UserDAO userDAO, GroupDAO groupDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }

    @Timed
    @POST
    @Path("/add")
    public Response add(User user) {
        final Integer count = userDAO.findCountUsersByGroup(user.groupId);
        if (count == 5) {
            return status(BAD_REQUEST)
                    .entity("More users than allowed in group")
                    .type("text/plain")
                    .build();
        }
        return ok(find(userDAO.insert(user.name, user.groupId)))
                .build();
    }

    @Timed
    @GET
    @Path("/{userId}")
    public User find(@PathParam("userId") Integer userId) {
        final User user = userDAO.findById(userId);
        user.groupName = groupDAO.findGroupById(user.groupId).name;
        return user;
    }

    @Timed
    @GET
    @Path("/all")
    public List<User> all() {
        final List<User> users = userDAO.all();
        users.stream().forEach(u -> u.groupName = groupDAO.findGroupById(u.groupId).name);
        return users;
    }


}
