package org.maxur.wmodel.domain;

import java.util.UUID;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/19/2015</pre>
 */
@SuppressWarnings("unused")
public abstract class Entity {

    private final String id;

    public Entity() {
        this.id = UUID.randomUUID().toString();
    }

    public Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
