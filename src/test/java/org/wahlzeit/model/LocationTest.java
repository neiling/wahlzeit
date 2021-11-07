package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    @Test
    public void testIsEqualDiffObj() {
        final Location location = new Location(0.1, 0.2, 0.3);
        assertFalse(location.equals(new Location(0.2, 0.2, 0.3)));
    }

    @Test
    public void testIsEqualNullObj() {
        final Location location = new Location(0.1, 0.2, 0.3);
        assertFalse(location.equals(null));
    }

    @Test
    public void testIsEqualSameObj() {
        final Location location = new Location(0.1, 0.2, 0.3);
        assertTrue(location.equals(location));
    }

    @Test
    public void testGetCoordinate() {
        final Location l0 = new Location(0.1, 0.2, 0.3);
        assertEquals(l0.getCoordinate(), new Coordinate(0.1, 0.2, 0.3));
        final Location l1 = new Location(new Coordinate(0.1, 0.2, 0.3));
        assertEquals(l1.getCoordinate(), new Coordinate(0.1, 0.2, 0.3));
    }

}
