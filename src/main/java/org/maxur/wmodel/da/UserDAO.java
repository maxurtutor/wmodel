package org.maxur.wmodel.da;

import org.maxur.wmodel.domain.User;
import org.maxur.wmodel.domain.UserRepository;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@RegisterMapper(UserMapper.class)
public interface UserDAO extends UserRepository {

    @SqlUpdate("insert into t_user (name, group_id) values (:name, :group_id)")
    @GetGeneratedKeys
    @Override
    Integer insert(@Bind("name") String name, @Bind("group_id") Integer userId);


    @SqlUpdate("update t_user set name = :user.name, group_id = :user.groupId where user_id = :user.id")
    @Override
    void amend(@BindBean("user") User user);

    @SqlQuery("select * from t_user where user_id = :user_id")
    @Override
    User find(@Bind("user_id") Integer id);

    @SqlQuery("select * from t_user")
    @Override
    List<User> findAll();

    @SqlQuery("select count(*) from t_user where group_id = :group_id")
    @Override
    Integer findCountUsersByGroup(@Bind("group_id") Integer groupId);

}
