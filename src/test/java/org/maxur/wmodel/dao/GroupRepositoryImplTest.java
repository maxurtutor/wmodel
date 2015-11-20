package org.maxur.wmodel.dao;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;
import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.NotFoundException;
import org.skife.jdbi.v2.DBI;

import static org.junit.Assert.assertEquals;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class GroupRepositoryImplTest {

    public static final Group FAKE_GROUP = Group.make(2, "Testers");

    @Tested
    GroupRepositoryImpl repository;

    @Injectable
    DBI dbi;

    @Test
    public void testFind(@Mocked GroupDAO groupDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            groupDAO.find(2);
            result = FAKE_GROUP;
        }};
        final Group result = repository.find(2);
        assertEquals("Testers", result.getName());
        assertEquals(2, result.getId());
    }

    @Test(expected = NotFoundException.class)
    public void testFindFull(@Mocked GroupDAO groupDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            groupDAO.find(2);
            result = null;
        }};
        repository.find(2);
    }


}