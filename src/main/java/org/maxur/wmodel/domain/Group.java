package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group {

    @JsonProperty
    public int id;

    @JsonProperty
    public String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
