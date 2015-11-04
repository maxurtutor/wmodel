package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.maxur.wmodel.domain.ServiceLocator.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class User {

    @JsonProperty
    public int id;

    @JsonProperty
    public int groupId;

    @JsonProperty
    public String name;

    public User() {
    }

    public User(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    @JsonProperty
    public Group getGroup() {
        final GroupRepository groupRepository = service(GroupRepository.class);
        return groupRepository.find(groupId);
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
}
