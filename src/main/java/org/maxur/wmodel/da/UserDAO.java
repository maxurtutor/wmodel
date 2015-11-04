package org.maxur.wmodel.da;

import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlUpdate("insert into user (name) values (:name)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name);

    @SqlQuery("select * from user where id = :id")
    User findById(@Bind("id") int id);

    @SqlQuery("select * from user")
    List<User> all();
}
