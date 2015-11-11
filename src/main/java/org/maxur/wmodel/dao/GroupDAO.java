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

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@RegisterMapper(GroupDAO.GroupMapper.class)
public interface GroupDAO extends DAO {

    @SqlQuery("SELECT  g.group_id, g.name, count(*) AS capacity \n" +
            "FROM t_group g JOIN t_user u ON g.group_id = u.group_id  \n" +
            "WHERE g.group_id = :group_id")
    GroupDAODTO find(@Bind("group_id") String groupId);

    @Override
    @SqlUpdate("UPDATE t_group SET name = :name, update_date = CURRENT_TIMESTAMP() WHERE group_id = :id")
    void amend(@BindBean Entity group);

    class GroupMapper implements ResultSetMapper<GroupDAODTO> {
        @Override
        public GroupDAODTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new GroupDAODTO(r.getString("group_id"), r.getString("name"), r.getInt("capacity"));
        }
    }

    class GroupDAODTO {

        final String groupId;
        final int capacity;
        final String name;

        public GroupDAODTO(String groupId, String name, int capacity) {
            this.name = name;
            this.groupId = groupId;
            this.capacity = capacity;
        }
    }
}