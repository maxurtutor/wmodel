package org.maxur.wmodel.domain;

import java.util.Objects;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private final String name;
    private String groupId;
    private Group group;

    private User(String id, String name, String groupId) {
        super(id);
        this.name = name;
        this.groupId = groupId;
    }

    private User(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public static User make(String id, String name, String groupId) {
        return new User(id, name, groupId);
    }

    public static User makeNew(String name, String groupId) {
        return new User(name, groupId);
    }

    public Group getGroup() {
        if (this.group == null) {
            final GroupRepository repository = service(GroupRepository.class);
            this.group = repository.find(groupId);
        }
        return this.group;
    }

    public void insertTo(Group group) throws ValidationException {
        assignToGroup(group);
        service(UserRepository.class).insert(this);
    }

    public void moveTo(Group group) throws ValidationException {
        if (Objects.equals(this.groupId, group.getId())) {
            return;
        }
        assignToGroup(group);
        service(UserRepository.class).amend(this);
    }

    private void assignToGroup(Group group) throws ValidationException {
        if (group.isComplete()) {
            throw new ValidationException("More users than allowed in group");
        }
        this.group = group;
        this.groupId = group.getId();
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }
}


