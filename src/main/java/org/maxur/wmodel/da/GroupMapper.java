package org.maxur.wmodel.da;

import org.maxur.wmodel.domain.Group;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class GroupMapper implements ResultSetMapper<Group> {

    @Override
    public Group map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Group(r.getInt("group_id"), r.getString("name"));
    }

}
