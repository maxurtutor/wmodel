package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.service.UserService;
import org.maxur.wmodel.service.ValidationException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author myunusov
 * @version 1.0
 * @since <pre>12.11.2015</pre>
 */ // The actual service
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;

    public UserResource(final UserService service) {
        this.service = service;
    }

    @Timed
    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public User add(User user) throws ValidationException {
        return service.insert(user);
    }

    @Timed
    @GET
    @Path("/user/{id}")
    public User find(@PathParam("id") Integer userId) throws ValidationException {
        return service.find(userId);
    }

    @Timed
    @GET
    @Path("/users")
    public List<User> all() {
        return service.findAll();
    }

}
