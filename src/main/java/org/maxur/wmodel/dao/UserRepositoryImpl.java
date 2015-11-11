package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.GroupRepository;
import org.maxur.wmodel.service.UnitOfWorkFactory;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.maxur.wmodel.dao.Lazy.lazy;
import static org.maxur.wmodel.dao.LazyInvocationHandler.proxy;


/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

    final private GroupRepository groupRepository;
    private final UnitOfWorkFactory unitOfWorkFactory;
    final private UserDAO dao;

    @Inject
    public UserRepositoryImpl(UserDAO dao, GroupRepository groupRepository, UnitOfWorkFactory unitOfWorkFactory) {
        this.dao = dao;
        this.groupRepository = groupRepository;
        this.unitOfWorkFactory = unitOfWorkFactory;
    }

    @Override
    public User find(String userId) {
        final UserDAO.UserDAODTO dto = dao.find(userId);
        checkNotNull(dto, userId);
        final User user = new User(dto.userId, dto.name, proxyGroup(dto), this);
        unitOfWorkFactory.provide().change(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return dao.findAll()
                .stream()
                .map(dto -> new User(dto.userId, dto.name, proxyGroup(dto), this))
                .collect(toList());
    }

    private Group proxyGroup(UserDAO.UserDAODTO dto) {
        final Lazy<Group> lazy = lazy(dto.groupId, groupRepository::find);
        return (Group) proxy(Group.class, lazy);
    }

    @Override
    public void insert(User user) {
        dao.insert(user);
    }

    @Override
    public void amend(User user) {
        dao.amend(user);
    }
}
