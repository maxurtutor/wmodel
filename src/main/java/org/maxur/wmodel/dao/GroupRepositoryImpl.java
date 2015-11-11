package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.service.GroupRepository;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class GroupRepositoryImpl extends AbstractRepository implements GroupRepository {

    final private DBI dbi;

    private final UnitOfWork unitOfWork;

    @Inject
    public GroupRepositoryImpl(DBI dbi, UnitOfWork unitOfWork) {
        this.dbi = dbi;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Group find(String groupId) {
        final GroupDAO.GroupDAODTO dto = dao().find(groupId);
        checkNotNull(dto, groupId);
        final Group group = new Group(dto.groupId, dto.name, dto.capacity);
        unitOfWork.change(group);
        return group;
    }

    private GroupDAO dao() {
        return dbi.onDemand(GroupDAO.class);
    }
}
