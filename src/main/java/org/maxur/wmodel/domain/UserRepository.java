package org.maxur.wmodel.domain;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public abstract class UserRepository extends Repository<User> {

    @Override
    protected String getTypeName() {
        return "User";
    }

    public abstract List<User> findAll();

    public abstract Integer insert(User user);

    public abstract Integer findCountUsersByGroup(int groupId);

}
