package org.maxur.wmodel.service;

import org.maxur.wmodel.da.UserDAO;
import org.maxur.wmodel.domain.User;

import java.util.List;

import static org.maxur.wmodel.service.ServiceLocator.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class UserRepository implements Repository<User> {

    private final UserDAO userDAO;

    public UserRepository() {
        this.userDAO = service(UserDAO.class);
    }

    @Override
    public User find(final Integer userId) {
        return userDAO.findById(userId);
    }

    @Override
    public List<User> findAll() {
        return userDAO.all();
    }
}
