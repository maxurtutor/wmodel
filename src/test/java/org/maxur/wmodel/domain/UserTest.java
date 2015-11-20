package org.maxur.wmodel.domain;

import mockit.Expectations;
import mockit.Mocked;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest  {

    public static final User FAKE_USER = User.make("u1", "Name", "g2");
    public static final Group FAKE_GROUP = Group.make("g2", "Testers", 4);

    @Test
    public void testFindWithLazyGroup(
        @Mocked ServiceLocator locator,
        @Mocked GroupRepository groupRepository
    ) throws Exception {
        new Expectations() {{
            new ServiceLocatorProvider(locator);
            locator.getService(GroupRepository.class);
            result = groupRepository;
            groupRepository.find("g2");
            result = FAKE_GROUP;
        }};
        Group result = FAKE_USER.getGroup();
        assertEquals("Testers", result.getName());
    }


}
