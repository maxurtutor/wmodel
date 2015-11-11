package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Entity;
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
public interface UserDAO extends DAO {

    @SqlQuery("SELECT * FROM t_user WHERE user_id = :user_id")
    UserDAODTO find(@Bind("user_id") String userId);

    @SqlQuery("SELECT * FROM t_user")
    List<UserDAODTO> findAll();

    @Override
    @SqlUpdate("INSERT INTO t_user (user_id, name, group_id) VALUES (:user.id, :user.name, :user.groupId)")
    void insert(@BindBean("user") Entity user);

    @Override
    @SqlBatch("INSERT INTO t_user (user_id, name, group_id) VALUES (:id, :name, :groupId)")
    void insertAll(@BindBean List<Entity> users);

    @Override
    @SqlUpdate("UPDATE t_user SET name = :user.name, group_id = :user.groupId WHERE user_id = :user.id")
    void amend(@BindBean("user") Entity user);

    class UserMapper implements ResultSetMapper<UserDAODTO> {
        public UserDAODTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new UserDAODTO(r.getString("user_id"), r.getString("name"), r.getString("group_id"));
        }
    }

    class UserDAODTO {

        final String userId;
        final String name;
        final String groupId;

        public UserDAODTO(String userId, String name, String groupId) {

            this.userId = userId;
            this.name = name;
            this.groupId = groupId;
        }
    }
}
