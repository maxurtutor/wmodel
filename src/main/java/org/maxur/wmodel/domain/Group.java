package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
@SuppressWarnings("unused")
public class Group {

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getGroupId() {
        return id;
    }

    public void setGroupId(int groupId) {
        this.id = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
