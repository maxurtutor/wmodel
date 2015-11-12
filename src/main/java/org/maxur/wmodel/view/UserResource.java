package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>12.11.2015</pre>
 */ // The actual service
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final DBI dbi;

    public UserResource(DBI dbi) {
        this.dbi = dbi;
    }

    @Timed
    @POST
    @Path("/user")
    public User add(String name) {
        return find(dao().insert(name));
    }

    @Timed
    @GET
    @Path("/user/{id}")
    public User find(@PathParam("id") Integer id) {
        return dao().findById(id);
    }

    @Timed
    @GET
    @Path("/users")
    public List<User> all() {
        return dao().findAll();
    }

    private UserDAO dao() {
        return dbi.onDemand(UserDAO.class);
    }


}
