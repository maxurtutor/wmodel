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
import static org.maxur.wmodel.view.dto.UserResponseDTO.dto;


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
    public UserResponseDTO add(UserRequestDTO dto) throws ValidationException {
        final User user = dto.assemble();
        return dto(user.insert());
    }

    @Timed
    @GET
    @Path("/user/{id}")
    public UserResponseDTO find(@PathParam("id") String userId) throws ValidationException {
        return dto(repository.find(userId));
    }

    @Timed
    @GET
    @Path("/users")
    public List<UserResponseDTO> all() {
        return repository
                .findAll()
                .stream()
                .map(UserResponseDTO::dto)
                .collect(toList());
    }

}
