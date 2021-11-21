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
        final Location location = new Location(0.1, 0.2, 0.3);
        assertNotEquals(location, new Location(0.2, 0.2, 0.3));
    }

    @Test
    public void testIsEqualNullObj() {
        final Location location = new Location(0.1, 0.2, 0.3);
        assertFalse(location.equals(null));
    }

    @Test
    public void testIsEqualSameObj() {
        final Location location = new Location(0.1, 0.2, 0.3);
        assertEquals(location, location);
    }

    @Test
    public void testGetCoordinate() {
        final Location l0 = new Location(0.1, 0.2, 0.3);
        assertEquals(l0.getCoordinate(), new CartesianCoordinate(0.1, 0.2, 0.3));
        final Location l1 = new Location(new CartesianCoordinate(0.1, 0.2, 0.3));
        assertEquals(l1.getCoordinate(), new CartesianCoordinate(0.1, 0.2, 0.3));
    }

    @Test
    public void testReadFrom() throws SQLException {
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.3);
        final Location location = new Location(rset);
        assertEquals(location, new Location(1.1, 1.2, 1.3));
    }

    @Test
    public void testReadFromTwice() throws SQLException {
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.3);
        final Location location = new Location(rset);
        assertFalse(location.isDirty());
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.4);
        location.readFrom(rset);
        assertEquals(location, new Location(1.1, 1.2, 1.4));
        assertTrue(location.isDirty());
    }

    @Test
    public void testWriteOn() throws SQLException {
        final Location  location = new Location(1.1, 1.2, 1.3);
        location.writeOn(rset);
        verify(rset, times(1)).updateDouble(eq("coordinate_x"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_y"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_z"), anyDouble());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIdAsString() {
        final Location  location = new Location(1.1, 1.2, 1.3);
        location.getIdAsString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testWriteId() {
        final Location  location = new Location(1.1, 1.2, 1.3);
        location.writeId(null, 0);
    }

    @Test
    public void testHashCode() {
        final Location  location = new Location(1.1, 1.2, 1.3);
        assertEquals(719355842, location.hashCode());
    }

}
