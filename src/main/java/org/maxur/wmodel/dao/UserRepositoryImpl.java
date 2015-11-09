package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.GroupRepository;
import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.maxur.wmodel.domain.Lazy.lazy;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UserRepositoryImpl implements UserRepository {

    final private UserDAO dao;
    final private GroupRepository groupRepository;

    @Inject
    public UserRepositoryImpl(UserDAO dao, GroupRepository groupRepository) {
        this.dao = dao;
        this.groupRepository = groupRepository;
    }

    @Override
    public User find(@Bind("user_id") String userId) {
        final UserDAO.UserDAODTO dto = dao.find(userId);
        return User.make(dto.userId, dto.name, lazy(dto.groupId, groupRepository::find));
    }

    @Override
    public List<User> findAll() {
        return dao.findAll()
                .stream()
                .map(dto -> User.make(dto.userId, dto.name, lazy(dto.groupId, groupRepository::find)))
                .collect(toList());
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
