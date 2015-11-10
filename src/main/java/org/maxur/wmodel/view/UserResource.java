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

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserFactory userFactory;

    @Inject
    public UserResource(
            UserRepository userRepository,
            GroupRepository groupRepository,
            UserFactory userFactory
    ) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.userFactory = userFactory;
    }

    @Timed
    @GET
    @Path("/all")
    public List<UserResponseDTO> all() {
        return userRepository
                .findAll()
                .stream()
                .map(UserResponseDTO::from)
                .collect(toList());
    }

    @Timed
    @GET
    @Path("/{userId}")
    public UserResponseDTO find(@PathParam("userId") String userId) {
        final User user = userRepository.find(userId);
        return UserResponseDTO.from(user);
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserResponseDTO add(UserRequestDTO dto) throws ValidationException {
        final GroupImpl group = groupRepository.find(dto.groupId);
        final User user = userFactory.create(dto.name, group);
        user.insertTo(group);
        return UserResponseDTO.from(user);
    }

    @Timed
    @PUT
    @Path("/{userId}")
    public UserResponseDTO move(@PathParam("userId") String userId, String groupId) throws ValidationException {
        final User user = userRepository.find(userId);
        final GroupImpl group = groupRepository.find(groupId);
        user.moveTo(group);
        return UserResponseDTO.from(user);
    }

}
