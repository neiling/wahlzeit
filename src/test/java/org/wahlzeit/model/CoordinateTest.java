package org.wahlzeit.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CoordinateTest {

    @Mock
    private ResultSet rset;

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

    @Test
    public void testReadFrom() throws SQLException {
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.3);
        final Coordinate coordinate = new Coordinate(rset);
        assertEquals(coordinate, new Coordinate(1.1, 1.2, 1.3));
    }

    @Test
    public void testReadFromTwice() throws SQLException {
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.3);
        final Coordinate coordinate = new Coordinate(rset);
        assertFalse(coordinate.isDirty());
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.4);
        coordinate.readFrom(rset);
        assertEquals(coordinate, new Coordinate(1.1, 1.2, 1.4));
        assertTrue(coordinate.isDirty());
    }

    @Test
    public void testWriteOn() throws SQLException {
        final Coordinate coordinate = new Coordinate(1.1, 1.2, 1.3);
        coordinate.writeOn(rset);
        verify(rset, times(1)).updateDouble(eq("coordinate_x"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_y"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_z"), anyDouble());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIdAsString() {
        final Coordinate coordinate = new Coordinate(1.1, 1.2, 1.3);
        coordinate.getIdAsString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testWriteId() {
        final Coordinate coordinate = new Coordinate(1.1, 1.2, 1.3);
        coordinate.writeId(null, 0);
    }

    @Test
    public void testHashCode() {
        final Coordinate coordinate = new Coordinate(1.1, 1.2, 1.3);
        assertEquals(719355811, coordinate.hashCode());
    }

}
