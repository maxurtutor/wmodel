package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maxur.wmodel.da.GroupDAO;
import org.maxur.wmodel.da.UserDAO;

import static org.maxur.wmodel.service.ServiceLocator.service;

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
        final GroupDAO groupDAO = service(GroupDAO.class);
        return groupDAO.findGroupById(groupId);
    }

    public User insert() {
        final UserDAO userDAO = service(UserDAO.class);
        final Integer countUsersByGroup =
                userDAO.findCountUsersByGroup(groupId);
        if (countUsersByGroup == 5) {
            throw new IllegalStateException("User limit is overflow");
        }
        this.id = userDAO.insert(name, groupId);
        return this;
    }
}
