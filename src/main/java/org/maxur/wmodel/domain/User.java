package org.maxur.wmodel.domain;

import static org.maxur.wmodel.domain.ServiceLocator.service;

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
            final GroupRepository groupRepository = service(GroupRepository.class);
            this.group = groupRepository.find(groupId);
        }
        return this.group;
    }

    public User insert() {
        final UserRepository userRepository = service(UserRepository.class);
        final Integer countUsersByGroup =
                userRepository.findCountUsersByGroup(groupId);
        if (countUsersByGroup == 5) {
            throw new IllegalStateException("User limit is overflow");
        }
        this.id = userRepository.insert(name, groupId);
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
