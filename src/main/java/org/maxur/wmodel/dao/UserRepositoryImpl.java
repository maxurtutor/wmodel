package org.maxur.wmodel.dao;

import net.sf.cglib.proxy.Enhancer;
import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.GroupRepository;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.maxur.wmodel.dao.Lazy.lazy;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class UserRepositoryImpl extends UserRepository {

    private final DBI dbi;

    private final GroupRepository groupRepository;

    private final UnitOfWork unitOfWork;

    @Inject
    public UserRepositoryImpl(DBI dbi, GroupRepository groupRepository, UnitOfWork unitOfWork) {
        this.dbi = dbi;
        this.unitOfWork = unitOfWork;
        this.groupRepository = groupRepository;
    }

    @Override
    protected User findById(String id) {
        final UserDAO.UserDAODTO dto = dao().find(id);
        if (dto == null) {
            return null;
        }
        final User user = new User(dto.id, dto.name, proxy(dto.groupId));
        unitOfWork.change(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return dao().findAll()
                .stream()
                .map(dto -> new User(dto.id, dto.name, proxy(dto.groupId)))
                .collect(toList());
    }

    private Group proxy(String groupId) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Group.class);
        enhancer.setCallback(new LazyInvocationHandler<>(lazy(groupId, groupRepository::find)));
        return (Group) enhancer.create(
                new Class[]{String.class, String.class, Integer.class},
                new Object[]{groupId, "", 0});
    }

    private UserDAO dao() {
        return dbi.onDemand(UserDAO.class);
    }

}
