package org.maxur.wmodel.domain;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/19/2015</pre>
 */
public abstract class Repository<T extends Entity> {

    public Optional<T> find(String id) {
        return ofNullable(findById(id));
    }

    protected abstract T findById(String id);

}
