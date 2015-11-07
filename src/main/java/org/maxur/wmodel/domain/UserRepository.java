package org.maxur.wmodel.domain;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public interface UserRepository {

    User find(final String userId);

    List<User> findAll();

    Integer findCountUsersByGroup(String groupId);

    void insert(User user);

    void amend(User user);
}
