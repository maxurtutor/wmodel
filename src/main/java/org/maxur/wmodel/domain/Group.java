package org.maxur.wmodel.domain;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group extends Entity {

    private String name;

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

    public void setName(String name) {
        this.name = name;
    }
}
