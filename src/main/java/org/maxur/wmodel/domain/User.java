package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class User extends Entity {

    @JsonProperty
    private String name;

    @JsonProperty
    private int groupId;

    private String groupName;

    public User() {
    }

    public User(int id, String name, int groupId) {
        super(id);
        this.name = name;
        this.groupId = groupId;
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

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @JsonProperty
    public String getGroupName() throws NotFoundException {
        if (groupName == null) {
            GroupRepository repository = service(GroupRepository.class);
            groupName = repository.find(groupId).getName();
        }
        return groupName;
    }

    public User insert() throws ValidationException {
        final UserRepository repository = service(UserRepository.class);
        final Integer count = repository.findCountUsersByGroup(this.groupId);
        if (count == 5) {
            throw new ValidationException("More users than allowed in group");
        }
        this.id = repository.insert(this);
        return this;
    }
}
