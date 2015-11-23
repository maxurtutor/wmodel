package org.maxur.wmodel.domain;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public abstract class UserRepository extends Repository<User> {

    public abstract List<User> findAll();

    public abstract void insert(User user);

}
