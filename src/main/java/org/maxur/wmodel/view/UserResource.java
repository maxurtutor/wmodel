package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.ServiceLocatorProvider;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.maxur.wmodel.domain.ValidationException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author myunusov
 * @version 1.0
 * @since <pre>12.11.2015</pre>
 */ // The actual service
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository repository;

    @Inject
    private ServiceLocatorProvider instance;

    @Inject
    public UserResource(final UserRepository repository) {
        this.repository = repository;
    }

    @Timed
    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public User add(User user) throws ValidationException {
        return user.insert();
    }

    @Timed
    @GET
    @Path("/user/{id}")
    public User find(@PathParam("id") Integer userId) throws ValidationException {
        return repository.find(userId);
    }

    @Timed
    @GET
    @Path("/users")
    public List<User> all() {
        return repository.findAll();
    }

}
