package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Entity;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.mixins.GetHandle;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>11.11.2015</pre>
 */
public abstract class Committer implements GetHandle {

    @Transaction
    public void commit(List<Entity> created, List<Entity> changed) {
        created.stream()
                .collect(groupingBy(e -> e.getClass()))
                .forEach((c, l) -> getDAOFor(c).insertAll(l));
        changed.forEach(e -> getDAOFor(e.getClass()).amend(e));
    }

    private DAO getDAOFor(Class<? extends Entity> aClass) {
        final String className = format("%s.%sDAO", this.getClass().getPackage().getName(), aClass.getSimpleName());
        final Class<? extends DAO> dao;
        try {
            dao = getDAOClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return getHandle().attach(dao);
    }

    private Class<? extends DAO> getDAOClass(String className) throws ClassNotFoundException {
        return Class.forName(className).asSubclass(DAO.class);
    }
}
