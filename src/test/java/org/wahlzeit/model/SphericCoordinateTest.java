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
public class SphericCoordinateTest {

    @Mock
    private ResultSet rset;

    @Test
    public void testReadFrom() throws SQLException {
        when(rset.getDouble(eq("coordinate_phi"))).thenReturn(0.9);
        when(rset.getDouble(eq("coordinate_theta"))).thenReturn(0.8);
        when(rset.getDouble(eq("coordinate_radius"))).thenReturn(10.0);
        final SphericCoordinate coordinate = new SphericCoordinate(rset);
        assertEquals(coordinate, new SphericCoordinate(0.9, 0.8, 10.0));
    }

    @Test
    public void testReadFromTwice() throws SQLException {
        when(rset.getDouble(eq("coordinate_phi"))).thenReturn(0.9);
        when(rset.getDouble(eq("coordinate_theta"))).thenReturn(0.8);
        when(rset.getDouble(eq("coordinate_radius"))).thenReturn(10.0);
        final SphericCoordinate coordinate = new SphericCoordinate(rset);
        assertFalse(coordinate.isDirty());
        when(rset.getDouble(eq("coordinate_phi"))).thenReturn(0.9);
        when(rset.getDouble(eq("coordinate_theta"))).thenReturn(0.9);
        when(rset.getDouble(eq("coordinate_radius"))).thenReturn(10.0);
        coordinate.readFrom(rset);
        assertEquals(coordinate, new SphericCoordinate(0.9, 0.9, 10.0));
        assertTrue(coordinate.isDirty());
    }

    @Test
    public void testWriteOn() throws SQLException {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        coordinate.writeOn(rset);
        verify(rset, times(1)).updateDouble(eq("coordinate_phi"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_theta"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_radius"), anyDouble());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIdAsString() {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        coordinate.getIdAsString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testWriteId() {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        coordinate.writeId(null, 0);
    }

    @Test
    public void testGetCartesianDistanceDiffCoor() {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        assertEquals(0.9995833854135667, coordinate.getCartesianDistance(new SphericCoordinate(0.9, 0.9, 10.0)), 0);
    }

    @Test
    public void testGetCartesianDistanceSameCoor() {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        assertEquals(0, coordinate.getCartesianDistance(new SphericCoordinate(0.9, 0.8, 10.0)), 0);
    }

    @Test
    public void testAsCartesianCoordinate() {
        final SphericCoordinate sphericCoordinate =
                new SphericCoordinate(0.8288490587889791, 0.8969196083497735, 2.083266665599966);
        final CartesianCoordinate cartesianCoordinate = sphericCoordinate.asCartesianCoordinate();
        final double DELTA = 1e-6; // 0.00001d
        assertEquals(1.1, cartesianCoordinate.getX(), DELTA);
        assertEquals(1.2, cartesianCoordinate.getY(), DELTA);
        assertEquals(1.3, cartesianCoordinate.getZ(), DELTA);
    }

    @Test
    public void testHashCode() {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        assertEquals(315914365, coordinate.hashCode());
    }

    @Test
    public void testEqualsExpectTrue() {
        final SphericCoordinate sphericCoordinate = new SphericCoordinate(0.8288490587889791, 0.8969196083497735, 2.083266665599966);
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1,1.2, 1.3);
        assertTrue(sphericCoordinate.isEqual(cartesianCoordinate));
    }

    @Test
    public void testEqualsExpectFalse() {
        final SphericCoordinate sphericCoordinate = new SphericCoordinate(0.8288490587889791, 0.8969196083497735, 2.083266665599966);
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1,1.2, 1.4);
        assertFalse(sphericCoordinate.isEqual(cartesianCoordinate));
    }

    @Test
    public void testEqualsNullObj() {
        final SphericCoordinate sphericCoordinate = new SphericCoordinate(0.8288490587889791, 0.8969196083497735, 2.083266665599966);
        assertFalse(sphericCoordinate.equals(null));
    }

}