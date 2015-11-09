package org.maxur.wmodel.domain;

import java.util.Objects;

import static org.maxur.wmodel.domain.Lazy.lazy;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private final String name;
    private final UserRepository repository;
    private String groupId;
    private Lazy<Group> group;

    public User(String id, String name, Lazy<Group> group, UserRepository repository) {
        super(id);
        this.name = name;
        this.group = group;
        this.repository = repository;
    }

    public User(String name, Lazy<Group> group, UserRepository repository) {
        this.name = name;
        this.group = group;
        this.repository = repository;
    }

    public Group getGroup() {
        return this.group.get();
    }

    public void insertTo(Group group) throws ValidationException {
        assignToGroup(group);
    }

    public void moveTo(Group group) throws ValidationException {
        if (Objects.equals(this.groupId, group.getId())) {
            return;
        }
        assignToGroup(group);
    }

    private void assignToGroup(Group group) throws ValidationException {
        if (group.isComplete()) {
            throw new ValidationException("More users than allowed in group");
        }
        this.group = lazy(group);
        this.groupId = group.getId();
    }

    @Override
    public void insert() {
        repository.insert(this);
    }

    @Override
    public void amend() {
        repository.amend(this);
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }
}


