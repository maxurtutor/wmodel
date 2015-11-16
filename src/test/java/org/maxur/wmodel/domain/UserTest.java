package org.maxur.wmodel.domain;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.Before;
import org.junit.Test;
import org.maxur.wmodel.dao.GroupDAO;
import org.maxur.wmodel.dao.UserDAO;
import org.maxur.wmodel.service.ValidationException;
import org.skife.jdbi.v2.DBI;

import static org.junit.Assert.assertEquals;

public class UserTest  {

    public User fakeUser = new User(1, "Name", 2);

    @Before
    public void setUp() throws Exception {
        fakeUser = new User(1, "Name", 2);
    }

    @Test
    public void testInsert(
        @Mocked ServiceLocator locator,
        @Mocked DBI dbi,
        @Mocked UserDAO userDAO
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(DBI.class);
            result = dbi;
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.insert("Name", 2);
            result = 1;
        }};
        final User result = fakeUser.insert();
        assertEquals("Name", result.getName());
        assertEquals(2, result.getGroupId());
        new Verifications() {{
            userDAO.insert("Name", 2);
            times = 1;
        }};
    }

    @Test(expected = ValidationException.class)
    public void testInsertWithOverflow(
        @Mocked ServiceLocator locator,
        @Mocked DBI dbi,
        @Mocked UserDAO userDAO,
        @Mocked final GroupDAO groupDAO
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(DBI.class);
            result = dbi;
            dbi.onDemand(UserDAO.class);
            result = userDAO;
            userDAO.findCountUsersByGroup(2);
            result = 5;
        }};
        fakeUser.insert();
    }

    @Test
    public void testFindWithLazyGroup(
        @Mocked ServiceLocator locator,
        @Mocked DBI dbi,
        @Mocked final GroupDAO groupDAO
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(DBI.class);
            result = dbi;
            dbi.onDemand(GroupDAO.class);
            result = groupDAO;
            groupDAO.findById(2);
            result = new Group(2, "Testers");
        }};
        String result = fakeUser.getGroupName();
        assertEquals("Testers", result);
    }


}
