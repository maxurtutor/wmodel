package org.maxur.wmodel.dao;

import static java.lang.String.format;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class AbstractRepository {

    protected void checkNotNull(Object dto, String key) {
        if (dto == null) {
            throw new IllegalArgumentException(format("Entity '%s' not found", key));
        }
    }
}
