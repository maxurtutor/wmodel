package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.GroupRepository;
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
public interface GroupDAO extends GroupRepository {

    @SqlQuery("SELECT  g.group_id, g.name, count(*) AS capacity FROM t_group g JOIN t_user WHERE  g.group_id = :group_id GROUP BY g.group_id, g.name;  ")
    Group find(@Bind("group_id") String groupId);

    class GroupMapper implements ResultSetMapper<Group> {

        @Override
        public Group map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return Group.make(r.getString("group_id"), r.getString("name"), r.getInt("capacity"));
        }

    }
}