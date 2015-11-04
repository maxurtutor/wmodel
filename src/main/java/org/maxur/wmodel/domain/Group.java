package org.maxur.wmodel.domain;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group {

    private final int groupId;

    private final String name;

    private Group(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public static Group make(int groupId, String name) {
        return new Group(groupId, name);
    }

    public int getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }
}
