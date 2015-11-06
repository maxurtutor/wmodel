package org.maxur.wmodel.service;

import org.jvnet.hk2.annotations.Service;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@Service
@Singleton
public class UserService {

    private final UserDAO userDAO;

    @Inject
    public UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User find(final Integer userId) {
        return userDAO.findById(userId);
    }

    public List<User> findAll() {
        return userDAO.all();
    }
}
