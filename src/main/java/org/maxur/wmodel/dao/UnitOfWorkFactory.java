package org.maxur.wmodel.dao;

import org.glassfish.hk2.api.Factory;
import org.skife.jdbi.v2.DBI;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UnitOfWorkFactory implements Factory<UnitOfWork> {

    private final DBI dbi;

    private final ThreadLocal<UnitOfWork> unitOfWork = new ThreadLocal<>();

    public UnitOfWorkFactory(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public UnitOfWork provide() {
        UnitOfWork result = unitOfWork.get();
        if (result == null) {
            result = new UnitOfWork(dbi);
            unitOfWork.set(result);
        }
        return result;
    }

    @Override
    public void dispose(UnitOfWork instance) {
        unitOfWork.remove();
    }
}
