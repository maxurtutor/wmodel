package org.maxur.wmodel.service;

import org.glassfish.hk2.api.Factory;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class UnitOfWorkFactory implements Factory<UnitOfWork> {

    private ThreadLocal<UnitOfWork> unitOfWork = new ThreadLocal<>();

    @Override
    public UnitOfWork provide() {
        UnitOfWork result = unitOfWork.get();
        if (result == null) {
            result = new UnitOfWork();
            unitOfWork.set(result);
        }
        return result;
    }

    @Override
    public void dispose(UnitOfWork instance) {
        unitOfWork.remove();
    }
}
