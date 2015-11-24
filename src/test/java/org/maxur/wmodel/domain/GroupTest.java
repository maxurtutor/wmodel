package org.maxur.wmodel.domain;

import mockit.Mocked;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>20.11.2015</pre>
 */
public class GroupTest {

    public static final Group FAKE_GROUP = Group.make("g2", "Testers", 4);
    public static final Group FAKE_COMPLETE_GROUP = Group.make("g3", "Managers", 5);

    @Test
    public void testAddUser(
            @Mocked ServiceLocator locator,
            @Mocked UserRepository userRepository
    ) throws Exception {
        final User user = new User("Name");
        FAKE_GROUP.addUser(user);
        assertEquals("Name", user.getName());
        assertEquals(FAKE_GROUP, user.getGroup());
    }

    @Test(expected = ValidationException.class)
    public void testAddUserWithOverflow(
            @Mocked ServiceLocator locator,
            @Mocked UserRepository userRepository
    ) throws Exception {
        FAKE_COMPLETE_GROUP.addUser(new User("Name"));
    }

}