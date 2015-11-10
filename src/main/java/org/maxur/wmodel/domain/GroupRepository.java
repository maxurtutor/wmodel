package org.maxur.wmodel.domain;

/**
 * @author Maxim Yunusov
 * @version 1.0
 * @since <pre>11/6/2015</pre>
 */
public interface GroupRepository {

    GroupImpl find(String groupId);

}
