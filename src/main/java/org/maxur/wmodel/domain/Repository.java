package org.maxur.wmodel.domain;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/19/2015</pre>
 */
public abstract class Repository<T extends Entity> {

    public T find(int id) throws NotFoundException {
        T result = findById(id);
        if (result == null) {
            throw new NotFoundException(getTypeName(), id);
        }
        return result;
    }

    protected abstract String getTypeName();

    protected abstract T findById(int id);
}
