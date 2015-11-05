package org.maxur.wmodel.service;

import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class UserService {

    private final UserDAO userDAO;

    private final GroupDAO groupDAO;

    public UserService(final UserDAO userDAO, final GroupDAO groupDAO) {
        this.userDAO = userDAO;
        this.groupDAO = groupDAO;
    }

    public User insert(final User user) {
        final Integer count = userDAO.findCountUsersByGroup(user.groupId);
        if (count == 5) {
            throw new IllegalStateException("More users than allowed in group");
        }
        return (find(userDAO.insert(user.name, user.groupId)));
    }

    public User find(final Integer userId) {
        final User user = userDAO.findById(userId);
        user.groupName = groupDAO.findGroupById(user.groupId).name;
        return user;
    }

    public List<User> findAll() {
        final List<User> users = userDAO.all();
        users.stream().forEach(u -> u.groupName = groupDAO.findGroupById(u.groupId).name);
        return users;
    }
}
