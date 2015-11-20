package org.maxur.wmodel.domain;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/19/2015</pre>
 */
@SuppressWarnings("unused")
public abstract class Entity {

    // XXX final
    private int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
