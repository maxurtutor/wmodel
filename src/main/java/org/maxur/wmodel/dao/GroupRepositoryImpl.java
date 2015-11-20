package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.GroupRepository;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class GroupRepositoryImpl extends GroupRepository {

    private final DBI dbi;

    @Inject
    public GroupRepositoryImpl(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public Group findById(int id) {
        return dbi.onDemand(GroupDAO.class).find(id);
    }

}
