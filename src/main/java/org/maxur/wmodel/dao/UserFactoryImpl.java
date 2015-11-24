package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserFactory;

import javax.inject.Inject;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>24.11.2015</pre>
 */
public class UserFactoryImpl implements UserFactory {

    private final UnitOfWork unitOfWork;

    @Inject
    public UserFactoryImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User make(String name) {
        final User user = new User(name);
        unitOfWork.create(user);
        return user;
    }

}
