package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.Lazy.lazy;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private final String name;

    private Lazy<Group> group;

    private User(String id, String name, Lazy<Group> group) {
        super(id);
        this.name = name;
        this.group = group;
    }

    private User(String name) {
        this.name = name;
    }

    public static User make(String id, String name, Lazy<Group> group) {
        return new User(id, name, group);
    }

    public static User makeNew(String name) {
        return new User(name);
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return this.group.get();
    }

    void setGroup(Group group) {
        this.group = lazy(group);
    }

    public String getGroupId() {
        return this.group.getId();
    }
}
