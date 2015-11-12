package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class User {

    @JsonProperty
    public final int id;

    @JsonProperty
    public final String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
