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

    public void insert() throws ValidationException {
        validate(groupId);
        service(UserRepository.class).insert(getId(), name, groupId);
    }

    public void moveTo(String newGroupId) throws ValidationException {
        if (Objects.equals(this.groupId, newGroupId)) {
            return;
        }
        validate(newGroupId);
        this.group = null;
        this.groupId = newGroupId;
        service(UserRepository.class).amend(this);
    }

    private void validate(String groupId) throws ValidationException {
        final Integer count = service(UserRepository.class).findCountUsersByGroup(groupId);
        if (count == 5) {
            throw new ValidationException("More users than allowed in group");
        }
    }

    public String getName() {
        return name;
    }
}


