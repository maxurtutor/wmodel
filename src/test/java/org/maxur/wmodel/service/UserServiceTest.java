package org.maxur.wmodel.service;

import mockit.*;
import org.junit.Test;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {

    public static final User FAKE_USER = new User(1, "Name", 2);

    @Tested(fullyInitialized = true)
    UserService service;

    @Injectable
    DBI dbi;

    @Test
    public void testFind(@Mocked final UserDAO userDAO, @Mocked final GroupDAO groupDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findById(1);
            result = FAKE_USER;
        }};
        final User result = service.find(1);
        assertEquals("Name", result.getName());
        assertEquals(2, result.getGroupId());
    }

    @Test
    public void testFindAll(@Mocked final UserDAO userDAO, @Mocked final GroupDAO groupDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findAll();
            result = new User[]{FAKE_USER};
        }};
        final List<User> result = service.findAll();
        assertEquals(1, result.size());
        assertEquals(FAKE_USER, result.get(0));
    }

}