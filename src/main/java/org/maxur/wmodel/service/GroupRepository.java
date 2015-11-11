package org.maxur.wmodel.service;

import org.maxur.wmodel.domain.Group;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/6/2015</pre>
 */
public interface GroupRepository {

    Group find(String groupId);

}
