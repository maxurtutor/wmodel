package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Entity;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UnitOfWork {

    private final DBI dbi;
    private final List<Entity> created = new ArrayList<>();
    private final List<Entity> changed = new ArrayList<>();

    public UnitOfWork(DBI dbi) {
        this.dbi = dbi;
    }

    @Inject
    public void create(Entity entity) {
        created.add(entity);
    }

    public void change(Entity entity) {
        changed.add(entity);
    }


    public void commit() {
        dbi.onDemand(Committer.class)
                .commit(created, changed);
        clear();
    }

    public void clear() {
        created.clear();
        changed.clear();
    }
}
