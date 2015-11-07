package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group extends Entity {

    private final String name;

    private Group(String id, String name) {
        super(id);
        this.name = name;
    }

    public static Group make(String id, String name) {
        return new Group(id, name);
    }

    public String getName() {
        return name;
    }

    boolean isComplete() {
        final Integer count = service(UserRepository.class).findCountUsersByGroup(getId());
        return count == 5;
    }
}

