package org.maxur.wmodel.dao;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;
import org.maxur.wmodel.domain.Group;
import org.skife.jdbi.v2.DBI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class GroupRepositoryImplTest {


    @Tested
    GroupRepositoryImpl repository;

    @Injectable
    DBI dbi;

    @Test
    public void testFind(@Mocked GroupDAO groupDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            groupDAO.find("g2");
            result = new GroupDAO.GroupDAODTO("g2", "Testers", 4);
        }};
        final Group result = repository.find("g2").get();
        assertEquals("Testers", result.getName());
        assertEquals("g2", result.getId());
    }

    @Test
    public void testFindNull(@Mocked GroupDAO groupDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            groupDAO.find("g2");
            result = null;
        }};
        assertFalse(repository.find("g2").isPresent());
    }


}