package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.User;
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
public interface UserDAO {

    @SqlUpdate("INSERT INTO t_user (user_id, name, group_id) VALUES (:id, :name, :groupId)")
    void insert(@BindBean User user);

    @SqlQuery("SELECT * FROM t_user WHERE user_id = :userId")
    User find(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM t_user")
    List<User> findAll();

    @SqlQuery("SELECT count(*) FROM t_user WHERE group_id = :groupId")
    Integer findCountUsersByGroup(@Bind("groupId") String groupId);

    class UserMapper implements ResultSetMapper<User> {
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return User.make(r.getString("user_id"), r.getString("name"), r.getString("group_id"));
        }
    }
}