package org.maxur.wmodel.service;

import org.maxur.wmodel.domain.User;

import java.util.List;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public interface UserRepository {

    User find(final String userId);

    List<User> findAll();

}
