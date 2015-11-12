package org.maxur.wmodel.domain;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class EntityTest {

    @Test
    public void testGetId() throws Exception {
        final Entity entity = new Entity() {
        };
        assertNotNull(entity.getId());
    }
}
