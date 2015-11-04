package org.maxur.wmodel.service;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public interface Repository<T> {

    T find(Integer id);

    List<T> findAll();
}
