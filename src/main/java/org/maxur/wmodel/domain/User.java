package org.maxur.wmodel.domain;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private final String name;

    private Group group;

    public User(String id, String name, Group group) {
        super(id);
        this.name = name;
        this.group = group;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return this.group;
    }

    void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return this.group.getId();
    }
}
