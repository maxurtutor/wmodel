package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
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
public interface GroupDAO {

    @SqlQuery("SELECT  g.group_id, g.name, count(*) AS user_number \n" +
        "FROM t_group g JOIN t_user u ON g.group_id = u.group_id  \n" +
        "GROUP BY g.group_id, g.name\n" +
        "HAVING g.group_id = :group_id\n")
    Group find(@Bind("group_id") String groupId);

    class GroupMapper implements ResultSetMapper<Group> {
        @Override
        public Group map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return Group.make(r.getString("group_id"), r.getString("name"), r.getInt("user_number"));
        }

    }
}