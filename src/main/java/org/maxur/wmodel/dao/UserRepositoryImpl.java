package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.*;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.maxur.wmodel.dao.LazyInvocationHandler.proxy;
import static org.maxur.wmodel.dao.Lazy.lazy;


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
    public User find(@Bind("user_id") String userId) {
        final UserDAO.UserDAODTO dto = dao.find(userId);
        checkNotNull(dto, userId);
        final User user = new User(dto.userId, dto.name, makeLazyGroup(dto), this);
        unitOfWorkFactory.provide().change(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return dao.findAll()
                .stream()
                .map(dto -> new User(dto.userId, dto.name, makeLazyGroup(dto), this))
                .collect(toList());
    }

    private Group makeLazyGroup(UserDAO.UserDAODTO dto) {
        final Lazy<Group> lazy = lazy(dto.groupId, groupRepository::find);
        return (Group) proxy(Group.class, lazy);
    }

    @Override
    public void insert(@BindBean("user") User user) {
        dao.insert(user);
    }

    @Override
    public void amend(@BindBean("user") User user) {
        dao.amend(user);
    }
}
