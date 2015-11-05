package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
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

    @SqlUpdate("INSERT INTO t_user (name, group_id) VALUES (:name, :group_id)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name, @Bind("group_id") int userId);

    @SqlQuery("SELECT * FROM t_user WHERE user_id = :user_id")
    User findById(@Bind("user_id") int userId);

    @SqlQuery("SELECT * FROM t_user")
    List<User> all();

    @SqlQuery("SELECT count(*) FROM t_user WHERE group_id = :group_id")
    Integer findCountUsersByGroup(@Bind("group_id") int groupId);

    class UserMapper implements ResultSetMapper<User> {
        public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new User(r.getInt("user_id"), r.getString("name"), r.getInt("group_id"));
        }
    }
}
