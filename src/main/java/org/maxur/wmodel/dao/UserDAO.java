package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@RegisterMapper(UserDAO.UserMapper.class)
public interface UserDAO extends UserRepository {

    @SqlQuery("SELECT * FROM t_user WHERE user_id = :user_id")
    User find(@Bind("user_id") String userId);

    @SqlQuery("SELECT * FROM t_user")
    List<User> findAll();

    @SqlUpdate("INSERT INTO t_user (user_id, name, group_id) VALUES (:user.id, :user.name, :user.groupId)")
    void insert(@BindBean("user") User user);

    @SqlUpdate("UPDATE t_user SET name = :user.name, group_id = :user.groupId WHERE user_id = :user.id")
    void amend(@BindBean("user") User user);

    class UserMapper implements ResultSetMapper<User> {
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return User.make(r.getString("user_id"), r.getString("name"), r.getString("group_id"));
        }
    }
}
