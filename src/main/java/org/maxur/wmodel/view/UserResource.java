package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.service.UserService;

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

    private final UserService service;

    public UserResource(final UserService service) {
        this.service = service;
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(User user) {
        try {
            return ok(service.insert(user)).build();
        } catch (RuntimeException e) {
            return status(BAD_REQUEST).entity(e.getMessage())
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
