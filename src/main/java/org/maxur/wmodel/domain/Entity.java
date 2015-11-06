package org.maxur.wmodel.domain;

import java.util.UUID;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>06.11.2015</pre>
 */
public class Entity {

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
