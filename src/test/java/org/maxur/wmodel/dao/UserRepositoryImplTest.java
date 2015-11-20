package org.maxur.wmodel.dao;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;
import org.maxur.wmodel.domain.NotFoundException;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class UserRepositoryImplTest {

    public static final User FAKE_USER = User.make("u1", "Name", "g2");

    @Tested
    UserRepositoryImpl repository;

    @Injectable
    DBI dbi;

    @Test
    public void testFind(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.find("u1");
            result = FAKE_USER;
        }};
        final User result = repository.find("u1");
        assertEquals("Name", result.getName());
        assertEquals("g2", result.getGroupId());
    }

    @Test(expected = NotFoundException.class)
    public void testFindNull(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.find("u1");
            result = null;
        }};
        repository.find("u1");
    }

    @Test
    public void testFindAll(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findAll();
            result = new User[]{FAKE_USER};
        }};
        final List<User> result = repository.findAll();
        assertEquals(1, result.size());
        assertEquals(FAKE_USER, result.get(0));
    }

    @Test
    public void testInsert(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.insert(FAKE_USER);
        }};
        repository.insert(FAKE_USER);
    }

    @Test
    public void testFindCountUsersByGroup(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findCountUsersByGroup("g2");
            result = 5;
        }};
        final Integer result = repository.findCountUsersByGroup("g2");
        assertEquals(new Integer(5), result);
    }
}