package org.maxur.wmodel.domain;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class Lazy<T extends Entity> {

    private final String id;

    private final Function<String, Optional<T>> loader;

    private T value;

    private Lazy(String id, Function<String, Optional<T>> loader) {
        this.id = id;
        this.loader = loader;
        this.value = null;
    }

    private Lazy(T value) {
        this.value = value;
        this.id = value.getId();
        this.loader = null;
    }

    public static <C extends Entity> Lazy<C> lazy(String id, Function<String, Optional<C>> loader) {
        return new Lazy<>(id, loader);
    }

    public static <C extends Entity> Lazy<C> lazy(C value) {
        return new Lazy<>(value);
    }

    public T get() {
        if (value == null) {
            value = loader.apply(id).get();
        }
        return value;
    }

    public String getId() {
        return id;
    }
}
