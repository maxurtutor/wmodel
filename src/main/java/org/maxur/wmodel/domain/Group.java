package org.maxur.wmodel.domain;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class Group extends Entity {

    private final String name;
    private final int capacity;

    public Group(String id, String name, int capacity) {
        super(id);
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    boolean isComplete() {
        return capacity == 5;
    }

    @Override
    public void insert() {
        throw new UnsupportedOperationException("This operation is unsopperted yet");
    }

    @Override
    public void amend() {
        throw new UnsupportedOperationException("This operation is unsopperted yet");
    }
}

