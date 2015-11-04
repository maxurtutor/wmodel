package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.service.Repository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final Repository<User> repository;

    public UserResource(final Repository<User> repository) {
        this.repository = repository;
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(User user) {
        try {
            return Response.ok(user.insert()).build();
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
        return repository.find(userId);
    }

    @Timed
    @GET
    @Path("/all")
    public List<User> all() {
        return repository.findAll();
    }


}
