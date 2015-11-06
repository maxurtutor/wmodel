package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.ServiceLocatorProvider;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.maxur.wmodel.domain.ValidationException;
import org.maxur.wmodel.view.dto.UserRequestDTO;
import org.maxur.wmodel.view.dto.UserResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    @GET
    @Path("/all")
    public List<UserResponseDTO> all() {
        return repository
                .findAll()
                .stream()
                .map(UserResponseDTO::from)
                .collect(toList());
    }

    @Timed
    @GET
    @Path("/{userId}")
    public UserResponseDTO find(@PathParam("userId") String userId) {
        final User user = repository.find(userId);
        checkNotNull(user);
        return UserResponseDTO.from(user);
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserResponseDTO add(UserRequestDTO dto) throws ValidationException {
        final User user = dto.assemble();
        user.insert();
        return UserResponseDTO.from(user);
    }

    @Timed
    @PUT
    @Path("/{userId}")
    public UserResponseDTO move(@PathParam("userId") String userId, String groupId) throws ValidationException {
        final User user = repository.find(userId);
        checkNotNull(user);
        user.moveTo(groupId);
        return UserResponseDTO.from(user);
    }

    private void checkNotNull(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    }

}
