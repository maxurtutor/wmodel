package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.GroupImpl;
import org.maxur.wmodel.domain.UnitOfWorkFactory;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserFactory;
import org.maxur.wmodel.domain.UserRepository;

import javax.inject.Inject;

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
    public User create(String name, GroupImpl group) {
        final User user = new User(name, group, repository);
        unitOfWorkFactory.provide().create(user);
        return user;
    }
}