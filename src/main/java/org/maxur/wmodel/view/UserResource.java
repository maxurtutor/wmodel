package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.ServiceLocatorProvider;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.maxur.wmodel.domain.ValidationException;
import org.maxur.wmodel.view.dto.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/user")
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
    @PUT
    @Path("/{userId}")
    public UserDTO move(@PathParam("userId") Integer userId, Integer groupId) throws ValidationException {
        final User user = repository.find(userId);
        user.moveTo(groupId);
        return UserDTO.from(user);
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDTO add(UserDTO dto) throws ValidationException {
        final User user = dto.assemble();
        return UserDTO.from(user.insert());
    }

    @Timed
    @GET
    @Path("/{userId}")
    public UserDTO find(@PathParam("userId") Integer userId) {
        return UserDTO.from(repository.find(userId));
    }

    @Timed
    @GET
    @Path("/all")
    public List<UserDTO> all() {
        return repository
            .findAll()
            .stream()
            .map(UserDTO::from)
            .collect(toList());
    }

}
