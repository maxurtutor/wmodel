package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.service.ValidationException;
import org.skife.jdbi.v2.DBI;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class User {

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;

    @JsonProperty
    private int groupId;


    private String groupName;

    public User() {
    }

    public User(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String getGroupName() {
        if (groupName == null) {
            groupName = groupDAO().findById(groupId).getName();
        }
        return groupName;
    }

    public User insert() throws ValidationException {
        final UserDAO userDAO = userDAO();
        final Integer count = userDAO.findCountUsersByGroup(this.groupId);
        if (count == 5) {
            throw new ValidationException("More users than allowed in group");
        }
        this.id = userDAO.insert(this.name, this.groupId);
        return this;
    }


    private GroupDAO groupDAO() {
        return service(DBI.class).onDemand(GroupDAO.class);
    }

    private UserDAO userDAO() {
        return service(DBI.class).onDemand(UserDAO.class);
    }
}
