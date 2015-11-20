package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private final String name;

    private final String groupId;

    private Group group;

    private User(String id, String name, String groupId) {
        super(id);
        this.name = name;
        this.groupId = groupId;
    }

    private User(String name) {
        this.name = name;
        this.groupId = null;
    }

    public static User make(String id, String name, String groupId) {
        return new User(id, name, groupId);
    }

    public static User makeNew(String name) {
        return new User(name);
    }

    public String getName() {
        return name;
    }

    public Group getGroup() throws NotFoundException {
        if (group == null) {
            if (groupId == null) {
                return null;
            }
            group = service(GroupRepository.class).find(groupId);
        }
        return group;
    }

    void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        if (group != null) {
            return group.getId();
        }
        return groupId;
    }
}
