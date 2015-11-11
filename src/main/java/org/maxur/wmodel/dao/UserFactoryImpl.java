package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.service.UserFactory;

import javax.inject.Inject;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UserFactoryImpl implements UserFactory {

    private final UnitOfWork unitOfWork;

    @Inject
    public UserFactoryImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User create(String name, Group group) {
        final User user = new User(name, group);
        unitOfWork.create(user);
        return user;
    }
}
