package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Entity;

import java.util.List;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/11/2015</pre>
 */
public interface DAO {

    void insert(Entity user);

    void amend(Entity user);

    void insertAll(List<Entity> entities);
}
