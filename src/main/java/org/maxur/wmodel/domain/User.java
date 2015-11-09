package org.maxur.wmodel.domain;

import java.util.Objects;

import static org.maxur.wmodel.domain.Lazy.lazy;
import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private final String name;
    private String groupId;
    private Lazy<Group> group;

    private User(String id, String name, Lazy<Group> group) {
        super(id);
        this.name = name;
        this.group = group;
    }

    private User(String name, Lazy<Group> group) {
        this.name = name;
        this.group = group;
    }

    public static User make(String id, String name, Lazy<Group> group) {
        return new User(id, name, group);
    }

    public static User makeNew(String name, Lazy<Group> group) {
        return new User(name, group);
    }

    public Group getGroup() {
        return this.group.get();
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
        this.group = lazy(group);
        this.groupId = group.getId();
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }
}


