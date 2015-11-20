package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.*;
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

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    // XXX
    @Inject
    private ServiceLocatorProvider instance;

    @Inject
    public UserResource(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Timed
    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserResponseDTO add(UserRequestDTO dto) throws ValidationException {
        Group group = groupRepository.find(dto.groupId);
        User user = User.makeNew(dto.name);
        group.addUser(user);
        return dto(user);
    }

    @Timed
    @GET
    @Path("/user/{id}")
    public UserResponseDTO find(@PathParam("id") String userId) throws ValidationException {
        return dto(userRepository.find(userId));
    }

    @Timed
    @GET
    @Path("/users")
    public List<UserResponseDTO> all() {
        return userRepository
                .findAll()
                .stream()
                .map(UserResponseDTO::dto)
                .collect(toList());
    }

}
