package org.maxur.wmodel.dao;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import org.maxur.wmodel.domain.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/10/2015</pre>
 */
class LazyInvocationHandler<T extends Entity> implements InvocationHandler {

    private final Lazy<T> lazy;

    private T object;

    public static <C extends Entity> Object proxy(Class<C> clazz, Lazy<C> lazy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new LazyInvocationHandler<>(lazy));
        return enhancer.create();
    }

    public LazyInvocationHandler(Lazy<T> lazy) {
        this.object = null;
        this.lazy = lazy;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            if (object == null) {
                if (isEquals(method)) {
                    return args[0] != null
                        && (args[0] instanceof Entity)
                        && Objects.equals(lazy.getId(), ((Entity) args[0]).getId());
                } else if (isGetId(method)) {
                    return lazy.getId();
                } else {
                    object = lazy.get();
                }
            }
            result = method.invoke(object, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        }
        return result;
    }

    private boolean isGetId(Method method) {
        return method.getName().equals("getId")
            && method.getParameterTypes().length == 0
            && !void.class.equals(method.getReturnType());
    }

    private boolean isEquals(Method method) {
        return method.getName().equals("equals")
            && method.getParameterTypes().length == 1
            && boolean.class.equals(method.getReturnType());
    }

}