package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.ServiceLocatorProvider;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.ValidationException;
import org.maxur.wmodel.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserService service;

    @Inject
    private ServiceLocatorProvider instance;

    @Inject
    public UserResource(final UserService service) {
        this.service = service;
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public User add(User user) throws ValidationException {
        return user.insert();
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
