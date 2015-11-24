package org.maxur.wmodel.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
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

    @SqlQuery("SELECT  g.group_id, g.name, count(*) AS user_number \n" +
        "FROM t_group g JOIN t_user u ON g.group_id = u.group_id  \n" +
        "GROUP BY g.group_id, g.name\n" +
        "HAVING g.group_id = :group_id\n")
    GroupDAODTO find(@Bind("group_id") String groupId);

    class GroupMapper implements ResultSetMapper<GroupDAODTO> {
        @Override
        public GroupDAODTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new GroupDAODTO(r.getString("group_id"), r.getString("name"), r.getInt("user_number"));
        }
    }

    class GroupDAODTO {

        final String id;
        final int userNumber;
        final String name;

        public GroupDAODTO(String id, String name, int userNumber) {
            this.name = name;
            this.id = id;
            this.userNumber = userNumber;
        }
    }
}