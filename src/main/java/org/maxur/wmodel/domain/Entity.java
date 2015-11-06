package org.maxur.wmodel.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/19/2015</pre>
 */
@SuppressWarnings("unused")
public abstract class Entity {

    @JsonProperty
    protected int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
