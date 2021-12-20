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
public class LocationTest {

    @Mock
    private ResultSet rset;

    @Test
    public void testIsEqualDiffObj() {
        final Location location = new Location(new CartesianCoordinate(0.1, 0.2, 0.3));
        assertNotEquals(location, new Location(new CartesianCoordinate(0.2, 0.2, 0.3)));
    }

    @Test
    public void testIsEqualNullObj() {
        final Location location = new Location(new CartesianCoordinate(0.1, 0.2, 0.3));
        assertFalse(location.equals(null));
    }

    @Test
    public void testIsEqualSameObj() {
        final Location location = new Location(new CartesianCoordinate(0.1, 0.2, 0.3));
        assertEquals(location, location);
    }

    @Test
    public void testGetCoordinate() {
        final Location l0 = new Location(new CartesianCoordinate( 0.1, 0.2, 0.3));
        assertEquals(l0.getCoordinate(), new CartesianCoordinate(0.1, 0.2, 0.3));
        final Location l1 = new Location(new CartesianCoordinate(0.1, 0.2, 0.3));
        assertEquals(l1.getCoordinate(), new CartesianCoordinate(0.1, 0.2, 0.3));
    }

    @Test
    public void testReadFromReturnCartesian() throws SQLException {
        when(rset.getDouble(eq("coordinate_a"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_b"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_c"))).thenReturn(1.3);
        when(rset.getInt(eq("coordinate_type"))).thenReturn(AbstractCoordinate.CoordinateType.CARTESIAN.ordinal());
        final Location location = new Location(rset);
        assertEquals(location, new Location(new CartesianCoordinate(1.1, 1.2, 1.3)));
    }

    @Test
    public void testReadFromReturnSpheric() throws SQLException {
        when(rset.getDouble(eq("coordinate_a"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_b"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_c"))).thenReturn(1.3);
        when(rset.getInt(eq("coordinate_type"))).thenReturn(AbstractCoordinate.CoordinateType.SPHERIC.ordinal());
        final Location location = new Location(rset);
        assertEquals(location, new Location(new SphericCoordinate(1.1, 1.2, 1.3)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReadFromTwice() throws SQLException {
        when(rset.getDouble(eq("coordinate_a"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_b"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_c"))).thenReturn(1.3);
        when(rset.getInt(eq("coordinate_type"))).thenReturn(AbstractCoordinate.CoordinateType.CARTESIAN.ordinal());
        final Location location = new Location(rset);
        location.readFrom(rset);
    }

    @Test
    public void testWriteOn() throws SQLException {
        final Location  location = new Location(new CartesianCoordinate(1.1, 1.2, 1.3));
        location.writeOn(rset);
        verify(rset, times(1)).updateDouble(eq("coordinate_a"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_b"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_c"), anyDouble());
        verify(rset, times(1)).updateInt(eq("coordinate_type"), eq(AbstractCoordinate.CoordinateType.CARTESIAN.ordinal()));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIdAsString() {
        final Location  location = new Location(new CartesianCoordinate(1.1, 1.2, 1.3));
        location.getIdAsString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testWriteId() {
        final Location  location = new Location(new CartesianCoordinate(1.1, 1.2, 1.3));
        location.writeId(null, 0);
    }

    @Test
    public void testHashCode() {
        final Location  location = new Location(new CartesianCoordinate(1.1, 1.2, 1.3));
        assertEquals(719355842, location.hashCode());
    }

}
