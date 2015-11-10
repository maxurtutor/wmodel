package org.maxur.wmodel.domain;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public interface UserFactory {

    User create(String name, GroupImpl group);
}
