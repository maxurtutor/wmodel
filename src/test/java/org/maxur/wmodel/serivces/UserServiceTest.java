package org.maxur.wmodel.service;

import mockit.*;
import org.junit.Test;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.IDBI;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {

    @Tested(fullyInitialized = true)
    UserService service;

    @Injectable
    IDBI dbi;

    @Test
    public void testInsert(@Mocked final UserDAO userDAO, @Mocked final GroupDAO groupDAO) throws Exception {
        final User user = new User();
        user.name = "Name";
        user.groupId = 2;
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            userDAO.insert("Name", 2);
            result = 1;
            userDAO.findById(1);
            result = user;
        }};
        final User result = service.insert(user);
        assertEquals("Name", result.name);
        assertEquals(2, result.groupId);
        new Verifications() {{
            userDAO.insert("Name", 2);
            times = 1;
        }};
    }

    @Test(expected = IllegalStateException.class)
    public void testInsertWithOverflow(@Mocked final UserDAO userDAO) throws Exception {
        final User user = new User();
        user.name = "Name";
        user.groupId = 2;
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findCountUsersByGroup(2);
            result = 5;
        }};
        service.insert(user);
    }

    @Test
    public void testFind(@Mocked final UserDAO userDAO, @Mocked final GroupDAO groupDAO) throws Exception {
        final User user = new User();
        user.name = "Name";
        user.groupId = 2;
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            userDAO.findById(1);
            result = user;
        }};
        final User result = service.find(1);
        assertEquals("Name", result.name);
        assertEquals(2, result.groupId);
    }

    @Test
    public void testFindAll(@Mocked final UserDAO userDAO, @Mocked final GroupDAO groupDAO) throws Exception {
        final User user = new User();
        user.name = "Name";
        user.groupId = 2;
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            userDAO.findAll();
            result = new User[]{user};
        }};
        final List<User> result = service.findAll();
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

}