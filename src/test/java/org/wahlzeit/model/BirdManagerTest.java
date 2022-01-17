package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class BirdManagerTest {

    @Test
    public void testGetOrCreateBirdByTypeName() {
        final Bird bird = BirdManager.getInstance().getOrCreateBird("Great tit", "Parus");
        assertEquals(new Bird("Great tit", new BirdType("Parus")), bird);
        assertTrue(BirdManager.getInstance().getBirdTypeMap().containsKey("Parus"));
    }

    @Test
    public void testGetOrCreateBirdByType() {
        final Bird bird = BirdManager.getInstance().getOrCreateBird("Great tit", new BirdType("Parus"));
        assertEquals(new Bird("Great tit", new BirdType("Parus")), bird);
        assertTrue(BirdManager.getInstance().getBirdTypeMap().containsKey("Parus"));
    }
}