package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.*;

import javax.inject.Inject;

import static org.maxur.wmodel.domain.Lazy.lazy;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UserFactoryImpl implements UserFactory {

    private final UnitOfWorkFactory unitOfWorkFactory;

    private final UserRepository repository;

    @Inject
    public UserFactoryImpl(UnitOfWorkFactory unitOfWorkFactory, UserRepository repository) {
        this.unitOfWorkFactory = unitOfWorkFactory;
        this.repository = repository;
    }

    @Override
    public User create(String name, Group group) {
        final User user = new User(name, lazy(group), repository);
        unitOfWorkFactory.provide().create(user);
        return user;
    }
}
