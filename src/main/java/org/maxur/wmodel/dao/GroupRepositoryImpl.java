package org.maxur.wmodel.dao;

import org.maxur.wmodel.domain.GroupImpl;
import org.maxur.wmodel.domain.GroupRepository;

import javax.inject.Inject;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>09.11.2015</pre>
 */
public class GroupRepositoryImpl extends AbstractRepository implements GroupRepository {

    final private GroupDAO dao;

    @Inject
    public GroupRepositoryImpl(GroupDAO dao) {
        this.dao = dao;
    }

    @Override
    public GroupImpl find(String groupId) {
        final GroupDAO.GroupDAODTO dto = dao.find(groupId);
        checkNotNull(dto, groupId);
        return new GroupImpl(dto.groupId, dto.name, dto.capacity);
    }
}
