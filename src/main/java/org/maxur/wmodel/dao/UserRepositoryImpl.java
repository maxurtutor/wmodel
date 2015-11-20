package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class UserRepositoryImpl extends UserRepository {

    private final DBI dbi;

    @Inject
    public UserRepositoryImpl(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public User findById(int id) {
        return dbi.onDemand(UserDAO.class).find(id);
    }

    @Override
    public List<User> findAll() {
        return dbi.onDemand(UserDAO.class).findAll();
    }

    @Override
    public Integer insert(User user) {
        return dbi.onDemand(UserDAO.class).insert(user);
    }

    @Override
    public Integer findCountUsersByGroup(int groupId) {
        return dbi.onDemand(UserDAO.class).findCountUsersByGroup(groupId);
    }
}
