package org.maxur.wmodel.service;

import static java.lang.String.format;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>13.11.2015</pre>
 */
public class NotFoundException extends ValidationException {

    public NotFoundException(String entityType, int id) {
        super(format("%s (id='%s') is not found", entityType, id));
    }
}
