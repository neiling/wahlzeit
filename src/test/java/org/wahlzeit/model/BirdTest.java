package org.wahlzeit.model;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class BirdTest {

    @Test
    public void testGetBirdType() {
        final Bird bird = new Bird("Great tit", new BirdType("Parus"));
        assertEquals(new BirdType("Parus"), bird.getBirdType());
    }

    @Test
    public void testGetSpecies() {
        final Bird bird = new Bird("Great tit", new BirdType("Parus"));
        assertEquals("Great tit", bird.getSpecies());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIdAsString() {
        final Bird bird = new Bird("Great tit", new BirdType("Parus"));
        bird.getIdAsString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testWriteId() throws SQLException {
        final Bird bird = new Bird("Great tit", new BirdType("Parus"));
        bird.writeId(null, 0);
    }

    @Test
    public void testEquals() {
        final Bird bird = new Bird("Great tit", new BirdType("Parus"));
        assertFalse(bird.equals(null));
        assertTrue(bird.equals(bird));
        assertTrue(bird.equals(new Bird("Great tit", new BirdType("Parus"))));
        assertFalse(bird.equals(new Bird("Japanese tit", new BirdType("Parus"))));
        assertFalse(bird.equals(new Bird("Fire-capped tit", new BirdType("Cephalopyrus"))));
    }

    @Test
    public void testHashCode() {
        final Bird bird = new Bird("Great tit", new BirdType("Parus"));
        assertEquals(-1704227853, bird.hashCode());
    }
}