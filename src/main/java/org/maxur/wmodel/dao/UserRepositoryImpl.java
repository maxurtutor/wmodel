package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.service.GroupRepository;
import org.maxur.wmodel.service.UserRepository;
import org.skife.jdbi.v2.DBI;

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
    private final UnitOfWork unitOfWork;
    final private DBI dbi;

    @Inject
    public UserRepositoryImpl(DBI dbi, GroupRepository groupRepository, UnitOfWork unitOfWork) {
        this.dbi = dbi;
        this.groupRepository = groupRepository;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User find(String userId) {
        final UserDAO.UserDAODTO dto = dao().find(userId);
        checkNotNull(dto, userId);
        final User user = new User(dto.userId, dto.name, proxyGroup(dto));
        unitOfWork.change(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return dao().findAll()
                .stream()
                .map(dto -> new User(dto.userId, dto.name, proxyGroup(dto)))
                .collect(toList());
    }

    private UserDAO dao() {
        return dbi.onDemand(UserDAO.class);
    }

    private Group proxyGroup(UserDAO.UserDAODTO dto) {
        final Lazy<Group> lazy = lazy(dto.groupId, groupRepository::find);
        return (Group) proxy(Group.class, lazy);
    }

}
