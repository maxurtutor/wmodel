package org.maxur.wmodel.dao;

import mockit.*;
import org.junit.Test;
import org.maxur.wmodel.domain.Group;
import org.maxur.wmodel.domain.GroupRepository;
import org.maxur.wmodel.domain.User;
import org.skife.jdbi.v2.DBI;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>18.11.2015</pre>
 */
public class UserRepositoryImplTest {

    public static final Group FAKE_GROUP = Group.make("g2", "Testers", 4);
    public static final User FAKE_USER = new User("u1", "Name", FAKE_GROUP);
    public static final UserDAO.UserDAODTO USER_DAODTO = new UserDAO.UserDAODTO("u1", "Name", "g2");

    @Tested
    UserRepositoryImpl repository;

    @Injectable
    DBI dbi;

    @Injectable
    GroupRepository groupRepository;

    @Injectable
    UnitOfWork unitOfWork;


    @Test
    public void testFindWithLazy(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.find("u1");
            result = USER_DAODTO;
        }};
        final User result = repository.find("u1").get();
        assertEquals("Name", result.getName());
        assertEquals("g2", result.getGroupId());
        new Verifications() {{
            groupRepository.find("g2");
            times = 0;
        }};
    }

    @Test
    public void testFind(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.find("u1");
            result = USER_DAODTO;
            groupRepository.find("g2");
            result = Optional.of(FAKE_GROUP);
        }};
        final User result = repository.find("u1").get();
        assertEquals("Name", result.getName());
        assertEquals("g2", result.getGroupId());
        assertEquals("Testers", result.getGroup().getName());
    }

    @Test
    public void testFindNull(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.find("u1");
            result = null;
        }};
        assertFalse(repository.find("u1").isPresent());
    }

    @Test
    public void testFindAll(@Mocked UserDAO userDAO) throws Exception {
        new Expectations() {{
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findAll();
            result = new UserDAO.UserDAODTO[]{USER_DAODTO};
        }};
        final List<User> result = repository.findAll();
        assertEquals(1, result.size());
        assertEquals(FAKE_USER, result.get(0));
    }


}