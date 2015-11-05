package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group {

    @JsonProperty
    public int groupId;

    @JsonProperty
    public String name;

    public Group(int groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }
}
