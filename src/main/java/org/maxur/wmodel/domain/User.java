package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocator.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User {

    private final String name;
    private int groupId;
    private int id;
    private Group group;

    private User(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    public static User make(int id, String name, int groupId) {
        return new User(id, name, groupId);
    }

    public Group getGroup() {
        if (this.group == null) {
            final GroupRepository groupRepository = service(GroupRepository.class);
            this.group = groupRepository.find(groupId);
        }
        return this.group;
    }

    public User insert() {
        checkLimit(groupId);
        this.id = service(UserRepository.class).insert(name, groupId);
        return this;
    }

    public void moveTo(Integer newGroupId) {
        checkLimit(newGroupId);
        this.groupId = newGroupId;
        service(UserRepository.class).amend(this);
    }

    private void checkLimit(int groupId) {
        final Integer count = service(UserRepository.class).findCountUsersByGroup(groupId);
        if (count == 5) {
            throw new IllegalStateException("User limit is overflow");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public int getGroupId() {
        return groupId;
    }
}
