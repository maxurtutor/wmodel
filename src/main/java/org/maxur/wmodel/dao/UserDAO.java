package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
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
public interface UserDAO {

    @SqlUpdate("INSERT INTO t_user (name, group_id) VALUES (:name, :groupId)")
    @GetGeneratedKeys
    Integer insert(@BindBean User user);

    @SqlQuery("SELECT * FROM t_user WHERE user_id = :userId")
    User find(@Bind("userId") Integer userId);

    @SqlQuery("SELECT * FROM t_user")
    List<User> findAll();

    @SqlQuery("SELECT count(*) FROM t_user WHERE group_id = :groupId")
    Integer findCountUsersByGroup(@Bind("groupId") Integer groupId);

    class UserMapper implements ResultSetMapper<User> {
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return User.make(r.getInt("user_id"), r.getString("name"), r.getInt("group_id"));
        }
    }
}