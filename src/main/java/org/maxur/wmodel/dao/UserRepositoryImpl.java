package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.*;
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

    @Inject
    public UserRepositoryImpl(DBI dbi, GroupRepository groupRepository) {
        this.dbi = dbi;
        this.groupRepository = groupRepository;
    }

    private static Group proxy(final Lazy<Group> lazy) {

        return new Group(lazy.getId()) {

            @Override
            public void addUser(User user) throws ValidationException {
                lazy.get().addUser(user);
            }

            @Override
            public String getName() {
                return lazy.get().getName();
            }
        };
    }

    @Override
    protected User findById(String id) {
        final UserDAO.UserDAODTO dto = dao().find(id);
        if (dto == null) {
            return null;
        }
        return User.make(dto.id, dto.name, proxy(lazy(dto.groupId, groupRepository::find)));
    }

    @Override
    public List<User> findAll() {
        return dao().findAll()
            .stream()
                .map(dto -> User.make(dto.id, dto.name, proxy(lazy(dto.groupId, groupRepository::find))))
            .collect(toList());
    }

    @Override
    public void insert(User user) {
        dao().insert(new UserDAO.UserDAODTO(user.getId(), user.getName(), user.getGroupId()));
    }

    private UserDAO dao() {
        return dbi.onDemand(UserDAO.class);
    }

}
