package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User {

    @JsonProperty
    public int id;

    @JsonProperty
    public int groupId;

    @JsonProperty
    public String name;

    @JsonProperty
    public Group group;


    public User() {
    }

    public User(int id, String name, int groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
