package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Entity;
import org.skife.jdbi.v2.DBI;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UnitOfWork {

    private final DBI dbi;
    private final List<Entity> created = new ArrayList<>();
    private final List<Entity> changed = new ArrayList<>();
    private final List<Entity> deleted = new ArrayList<>();

    public UnitOfWork(DBI dbi) {
        this.dbi = dbi;
    }

    public void create(Entity entity) {
        created.add(entity);
    }

    public void change(Entity entity) {
        changed.add(entity);
    }

    public void commit() {
        created.forEach(e -> getDAOFor(e.getClass()).insert(e));
        changed.forEach(e -> getDAOFor(e.getClass()).update(e));
        deleted.forEach(e -> getDAOFor(e.getClass()).delete(e));
    }

    public void clear() {
        created.clear();
        changed.clear();
        deleted.clear();
    }

    private DAO getDAOFor(Class<? extends Entity> aClass) {
        Class<? extends Entity> clazz = aClass;
        final String className = format("%s.%sDAO", this.getClass().getPackage().getName(), clazz.getSimpleName());
        final Class<? extends DAO> dao;
        try {
            dao = getDAOClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return dbi.onDemand(dao);
    }

    private Class<? extends DAO> getDAOClass(String className) throws ClassNotFoundException {
        return Class.forName(className).asSubclass(DAO.class);
    }
}
