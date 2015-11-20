package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private String name;

    private String groupId;

    private Group group;

    private User(String id, String name, String groupId) {
        super(id);
        this.name = name;
        this.groupId = groupId;
    }

    public User(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public static User make(String id, String name, String groupId) {
        return new User(id, name, groupId);
    }

    public static User make(String name, String groupId) {
        return new User(name, groupId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public Group getGroup() throws NotFoundException {
        if (group == null) {
            group = service(GroupRepository.class).find(groupId);
        }
        return group;
    }

    public User insert() throws ValidationException {
        final UserRepository repository = service(UserRepository.class);
        final Integer count = repository.findCountUsersByGroup(this.groupId);
        if (count == 5) {
            throw new ValidationException("More users than allowed in group");
        }
        repository.insert(this);
        return this;
    }
}
