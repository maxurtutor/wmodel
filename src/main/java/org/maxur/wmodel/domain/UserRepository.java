package org.maxur.wmodel.domain;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public interface UserRepository {

    User find(final Integer userId);

    List<User> findAll();

    Integer findCountUsersByGroup(Integer groupId);

    Integer insert(String name, Integer userId);

    void amend(User user);

}
