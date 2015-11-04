package org.maxur.wmodel.domain;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public class ServiceLocator {

    private static ServiceLocator instance = new ServiceLocator();

    private ServiceMap<Object> services = new ServiceMap<>();

    public static <T> T service(final Class<T> clazz) {
        return instance.getService(clazz);
    }

    public static <T> void persist(final Class<T> clazz, final T service) {
        instance.putService(clazz, service);
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(final Class<T> clazz) {
        return (T) services.get(clazz);
    }

    public <T> void putService(final Class<T> clazz, final T service) {
        services.put(clazz, service);
    }

    private static class ServiceMap<T> extends ConcurrentHashMap<Class<? extends T>, T> {
    }

}
