package org.maxur.wmodel.domain;

import mockit.Expectations;
import mockit.Mocked;
import org.glassfish.hk2.api.ServiceLocator;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.maxur.wmodel.domain.Lazy.lazy;

public class UserTest  {

    public static final Group FAKE_GROUP = Group.make("g2", "Testers", 4);

    @Test
    public void testFindWithLazyGroup(
        @Mocked ServiceLocator locator,
        @Mocked final GroupRepository groupRepository
    ) throws Exception {
        User FAKE_USER = User.make("u1", "Name", lazy("g2", groupRepository::find));
        new Expectations() {{
            groupRepository.find("g2");
            result = Optional.of(FAKE_GROUP);
        }};
        Group result = FAKE_USER.getGroup();
        assertEquals("Testers", result.getName());
    }


}
