package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO dao;

    public UserResource(final UserDAO dao) {
        this.dao = dao;
    }

    @Timed
    @POST
    @Path("/add")
    public User add(String name) {
        return find(dao.insert(name));
    }

    @Timed
    @GET
    @Path("/{id}")
    public User find(@PathParam("id") Integer id) {
        return dao.findById(id);
    }

    @Timed
    @GET
    @Path("/all")
    public List<User> all(@PathParam("id") Integer id) {
        return dao.all();
    }


}
