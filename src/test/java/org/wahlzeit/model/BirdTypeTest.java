package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BirdTypeTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBirdTypeNullException() {
        new BirdType(null);
    }

    @Test
    public void testAddSubType() {
        final BirdType rootType = new BirdType("Paridae");
        final BirdType subType = new BirdType("Parus");
        rootType.addSubType(subType);
        assertTrue(rootType.isSubtype(subType));
        assertEquals(subType.getSuperType(), rootType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSubTypeNullException() {
        final BirdType rootType = new BirdType("Paridae");
        rootType.addSubType(null);
    }

    @Test
    public void testIsSubType() {
        final BirdType rootType = new BirdType("Paridae");
        final BirdType subType = new BirdType("Cephalopyrus");
        rootType.addSubType(subType);
        final BirdType subSubType = new BirdType("Fire-capped tit");
        subType.addSubType(subSubType);
        assertTrue(rootType.isSubtype(new BirdType("Fire-capped tit")));
        assertFalse(rootType.isSubtype(new BirdType("Parus")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsSubTypeNullException() {
        final BirdType rootType = new BirdType("Paridae");
        assertFalse(rootType.isSubtype(null));
    }

    @Test
    public void testHasTypeInstanceOf() {
        final BirdType rootType = new BirdType("Paridae");
        final BirdType subType = new BirdType("Cephalopyrus");
        rootType.addSubType(subType);
        final BirdType subSubType = new BirdType("Fire-capped tit");
        subType.addSubType(subSubType);
        final Bird bird = new Bird("Great tit", new BirdType("Fire-capped tit"));
        rootType.addSubType(subType);
        assertTrue(rootType.hasTypeInstanceOf(bird));
        assertTrue(subType.hasTypeInstanceOf(bird));
        assertTrue(subSubType.hasTypeInstanceOf(bird));
        assertFalse(rootType.hasTypeInstanceOf(null));
    }

    @Test
    public void testEquals() {
        final BirdType birdType = new BirdType("Parus");
        assertFalse(birdType.equals(null));
        assertTrue(birdType.equals(birdType));
        assertTrue(birdType.equals(new BirdType("Parus")));
        assertFalse(birdType.equals(new BirdType("Cephalopyrus")));
    }

}