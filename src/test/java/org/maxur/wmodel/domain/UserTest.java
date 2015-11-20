package org.maxur.wmodel.domain;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest  {

    public static final User FAKE_USER = User.make(1, "Name", 2);
    public static final Group FAKE_GROUP = Group.make(2, "Testers");

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testInsert(
        @Mocked ServiceLocator locator,
        @Mocked UserRepository userRepository
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(UserRepository.class);
            result = userRepository;
            userRepository.findCountUsersByGroup(2);
            result = 1;
        }};
        final User result = FAKE_USER.insert();
        assertEquals("Name", result.getName());
        assertEquals(2, result.getGroupId());
        new Verifications() {{
            userRepository.insert(FAKE_USER);
            times = 1;
        }};
    }

    @Test(expected = ValidationException.class)
    public void testInsertWithOverflow(
        @Mocked ServiceLocator locator,
        @Mocked UserRepository userRepository
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(UserRepository.class);
            result = userRepository;
            userRepository.findCountUsersByGroup(2);
            result = 5;
        }};
        FAKE_USER.insert();
    }

    @Test
    public void testFindWithLazyGroup(
        @Mocked ServiceLocator locator,
        @Mocked GroupRepository groupRepository
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(GroupRepository.class);
            result = groupRepository;
            groupRepository.find(2);
            result = FAKE_GROUP;
        }};
        Group result = FAKE_USER.getGroup();
        assertEquals("Testers", result.getName());
    }


}
