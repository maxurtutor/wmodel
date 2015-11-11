package org.maxur.wmodel.service;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.User;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public interface UserFactory {

    User create(String name, Group group);
}
