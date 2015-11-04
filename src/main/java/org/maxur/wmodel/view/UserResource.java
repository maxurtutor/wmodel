package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.maxur.wmodel.view.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository repository;

    public UserResource(final UserRepository repository) {
        this.repository = repository;
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(UserDTO dto) {
        try {
            final User user = dto.assemble();
            return Response.ok(UserDTO.from(user.insert())).build();
        } catch (RuntimeException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage())
                    .type("text/plain")
                    .build();
        }
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
