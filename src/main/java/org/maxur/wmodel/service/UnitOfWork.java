package org.maxur.wmodel.service;

import org.maxur.wmodel.domain.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UnitOfWork {

    private final List<Entity> created = new ArrayList<>();
    private final List<Entity> changed = new ArrayList<>();

    public void create(Entity entity) {
        created.add(entity);
    }

    public void change(Entity entity) {
        changed.add(entity);
    }

    public void commit() {
        created.forEach(Entity::insert);
        changed.forEach(Entity::amend);
    }

    public void clear() {
        created.clear();
        changed.clear();

    }
}
