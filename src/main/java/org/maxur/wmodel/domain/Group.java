package org.maxur.wmodel.domain;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group extends Entity {

    private final String name;

    private Group(int id, String name) {
        super(id);
        this.name = name;
    }

    public static Group make(int id, String name) {
        return new Group(id, name);
    }

    public String getName() {
        return name;
    }
}

