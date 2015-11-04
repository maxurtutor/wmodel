package org.maxur.wmodel.service;

import org.maxur.wmodel.da.GroupDAO;
import org.maxur.wmodel.da.UserDAO;
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
        final Integer countUsersByGroup =
                userDAO.findCountUsersByGroup(user.groupId);
        if (countUsersByGroup == 5) {
            throw new IllegalStateException("User limit is overflow");
        }
        return (find(userDAO.insert(user.name, user.groupId)));
    }

    public User find(final Integer userId) {
        final User user = userDAO.findById(userId);
        user.setGroup(groupDAO.findGroupById(user.groupId));
        return user;
    }

    public List<User> findAll() {
        final List<User> users = userDAO.all();
        users.stream().forEach(u -> u.setGroup(groupDAO.findGroupById(u.groupId)));
        return users;
    }
}
