package org.maxur.wmodel.service;

import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.IDBI;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class UserService {

    private final IDBI dbi;

    public UserService(DBI dbi) {
        this.dbi = dbi;
    }

    public User find(final Integer userId) throws NotFoundException {
        final User user = userDAO().findById(userId);
        checkEntity(user, userId, "User");
        loadGroup(user);
        return user;
    }

    private void checkEntity(Object entity, Integer entityId, String entityType) throws NotFoundException {
        if (entity == null) {
            throw new NotFoundException(entityType, entityId);
        }
    }

    public List<User> findAll() {
        final List<User> users = userDAO().findAll();
        users.stream().forEach(this::loadGroup);
        return users;
    }

    private void loadGroup(User user) {
        user.groupName = groupDAO().findById(user.groupId).name;
    }

    public User insert(final User user) throws ValidationException {
        checkGroupCapacity(user);
        try {
            return (find(userDAO().insert(user.name, user.groupId)));
        } catch (RuntimeException e) {
            throw new ValidationException("Constraint violation");
        }
    }

    private void checkGroupCapacity(User user) throws ValidationException {
        final Integer count = userDAO().findCountUsersByGroup(user.groupId);
        if (count == 5) {
            throw new ValidationException("More users than allowed in group");
        }
    }

    private UserDAO userDAO() {
        return dbi.onDemand(UserDAO.class);
    }

    private GroupDAO groupDAO() {
        return dbi.onDemand(GroupDAO.class);
    }

}