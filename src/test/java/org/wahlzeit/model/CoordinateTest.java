package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testGetDistanceDiffCoor() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertEquals(1.4177446878757824, coordinate.getDistance(new Coordinate(0.0, 2.1, 3.1)), 0);
    }

    @Test
    public void testGetDistanceSameCoor() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertEquals(0, coordinate.getDistance(new Coordinate(0.1, 1.1, 2.1)), 0);
    }

    @Test
    public void testIsEqualExpectFalse() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertFalse(coordinate.isEqual(new Coordinate(0.1, 1.1, 2.2)));
    }

    @Test
    public void testIsEqualExpectTrue() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertTrue(coordinate.isEqual(new Coordinate(0.1, 1.1, 2.1)));
    }

    @Test
    public void testIsEqualExpectTrueWithEpsilon() {
        final Coordinate coordinate = new Coordinate(0.7999999999999999, 1.1, 2.1);
        assertTrue(coordinate.isEqual(new Coordinate(0.8, 1.1, 2.1)));
    }

    @Test
    public void testIsEqualSameObj() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertTrue(coordinate.isEqual(coordinate));
    }

    @Test
    public void testIsEqualNullObj() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertFalse(coordinate.isEqual(null));
    }

    @Test
    public void testEqualsNullObj() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertFalse(coordinate.equals(null));
    }

    @Test
    public void testGetX() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertEquals(0.1, coordinate.getX(), 0.0);
    }

    @Test
    public void testGetY() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertEquals(1.1, coordinate.getY(), 0.0);
    }

    @Test
    public void testGetZ() {
        final Coordinate coordinate = new Coordinate(0.1, 1.1, 2.1);
        assertEquals(2.1, coordinate.getZ(), 0.0);
    }

}
