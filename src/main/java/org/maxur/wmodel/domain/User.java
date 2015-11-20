package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User extends Entity {

    private String name;

    private int groupId;

    private Group group;

    private User(int id, String name, int groupId) {
        super(id);
        this.name = name;
        this.groupId = groupId;
    }

    public static User make(int id, String name, int groupId) {
        return new User(id, name, groupId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupId() {
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
        // XXX
        this.setId(repository.insert(this));
        return this;
    }
}
