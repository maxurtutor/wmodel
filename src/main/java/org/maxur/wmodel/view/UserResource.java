package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.*;
import org.maxur.wmodel.view.dto.UserRequestDTO;
import org.maxur.wmodel.view.dto.UserResponseDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.lang.String.format;
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

    @Inject
    private ServiceLocatorProvider instance;

    @Inject
    public UserResource(final UserRepository userRepository, final GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
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
        checkNotNull(user, userId);
        return UserResponseDTO.from(user);
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserResponseDTO add(UserRequestDTO dto) throws ValidationException {
        final User user = dto.assemble();
        final Group group = groupRepository.find(dto.groupId);
        checkNotNull(group, dto.groupId);
        user.insertTo(group);
        return UserResponseDTO.from(user);
    }

    @Timed
    @PUT
    @Path("/{userId}")
    public UserResponseDTO move(@PathParam("userId") String userId, String groupId) throws ValidationException {
        final User user = userRepository.find(userId);
        final Group group = groupRepository.find(groupId);
        checkNotNull(user, userId);
        checkNotNull(group, groupId);
        user.moveTo(group);
        return UserResponseDTO.from(user);
    }

    private void checkNotNull(Entity entity, String key) {
        if (entity == null) {
            throw new IllegalArgumentException(format("Entity '%s' not found", key));
        }
    }

}
