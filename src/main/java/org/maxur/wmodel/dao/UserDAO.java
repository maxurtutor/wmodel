package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Entity;
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
public interface UserDAO extends DAO {

    @SqlUpdate("INSERT INTO t_user (user_id, name, group_id) VALUES (:id, :name, :groupId)")
    void insert(@BindBean Entity user);

    @SqlQuery("SELECT * FROM t_user WHERE user_id = :userId")
    UserDAODTO find(@Bind("userId") String userId);

    @SqlQuery("SELECT * FROM t_user")
    List<UserDAODTO> findAll();

    class UserMapper implements ResultSetMapper<UserDAODTO> {
        public UserDAODTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new UserDAODTO(r.getString("user_id"), r.getString("name"), r.getString("group_id"));
        }
    }

    class UserDAODTO {

        final String id;
        final String name;
        final String groupId;

        public UserDAODTO(String id, String name, String groupId) {

            this.id = id;
            this.name = name;
            this.groupId = groupId;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGroupId() {
            return groupId;
        }
    }
}