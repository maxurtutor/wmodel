package org.maxur.wmodel.view;

import com.codahale.metrics.annotation.Timed;
import org.maxur.wmodel.da.GroupDAO;
import org.maxur.wmodel.da.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.stream;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */ // The actual service
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;
    private final GroupDAO groupDAO;

    public UserResource(final DBI dbi) {
        this.userDAO = dbi.onDemand(UserDAO.class);
        this.groupDAO = dbi.onDemand(GroupDAO.class);

        try (Handle h = dbi.open()) {
            h.execute("create table t_group (group_id int primary key, name varchar(100))");
            h.execute("create table t_user (user_id int primary key auto_increment, name varchar(100), group_id int)");
            h.insert("insert into t_group (group_id, name) values (?, ?)", 1, "developers");
            String[] names = {"Ivanov", "Petrov", "Sidorov"};
            stream(names)
                    .forEach(name -> h.insert("insert into t_user (name, group_id) values (?, ?)", name, 1));
        }
    }

    @Timed
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(User user) {
        final Integer countUsersByGroup =
                userDAO.findCountUsersByGroup(user.groupId);
        if (countUsersByGroup == 5) {
            return Response.status(400)
                    .entity("User limit is overflow")
                    .type("text/plain")
                    .build();
        }
        return Response
                .ok(find(userDAO.insert(user.name, user.groupId)))
                .build();
    }

    @Timed
    @GET
    @Path("/{userId}")
    public User find(@PathParam("userId") Integer userId) {
        final User user = userDAO.findById(userId);
        user.setGroup(groupDAO.findGroupById(user.groupId));
        return user;
    }

    @Timed
    @GET
    @Path("/all")
    public List<User> all() {
        final List<User> users = userDAO.all();
        users.stream().forEach(u -> u.setGroup(groupDAO.findGroupById(u.groupId)));
        return users;
    }


}
