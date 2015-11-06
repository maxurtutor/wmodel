package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User {

    private final String name;
    private final int groupId;
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
            final GroupRepository repository = service(GroupRepository.class);
            this.group = repository.find(groupId);
        }
        return this.group;
    }

    public User insert() throws ValidationException  {
        final UserRepository repository = service(UserRepository.class);
        final Integer count = repository.findCountUsersByGroup(this.groupId);
        if (count == 5) {
            throw new ValidationException("More users than allowed in group");
        }
        this.id = repository.insert(this.name, this.groupId);
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}


