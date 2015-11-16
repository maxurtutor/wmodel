package org.maxur.wmodel.service;

import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.IDBI;

import javax.inject.Inject;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class UserService {

    private final IDBI dbi;

    @Inject
    public UserService(DBI dbi) {
        this.dbi = dbi;
    }

    public User find(final Integer userId) throws NotFoundException {
        final User user = userDAO().findById(userId);
        checkEntity(user, userId, "User");
        return user;
    }

    private void checkEntity(Object entity, Integer entityId, String entityType) throws NotFoundException {
        if (entity == null) {
            throw new NotFoundException(entityType, entityId);
        }
    }

    public List<User> findAll() {
        return userDAO().findAll();
    }

    private UserDAO userDAO() {
        return dbi.onDemand(UserDAO.class);
    }

}