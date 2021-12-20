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
    public void testGetFromResultSet() throws SQLException {
        when(rset.getDouble(eq("coordinate_a"))).thenReturn(0.9);
        when(rset.getDouble(eq("coordinate_b"))).thenReturn(0.8);
        when(rset.getDouble(eq("coordinate_c"))).thenReturn(10.0);
        final SphericCoordinate coordinate = SphericCoordinate.getFromResultSet(rset);
        assertEquals(coordinate, new SphericCoordinate(0.9, 0.8, 10.0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testReadFrom() throws SQLException {
        final SphericCoordinate coordinate = SphericCoordinate.getFromResultSet(rset);
        coordinate.readFrom(rset);
    }

    @Test
    public void testWriteOn() throws SQLException {
        final SphericCoordinate coordinate = new SphericCoordinate(0.9, 0.8, 10.0);
        coordinate.writeOn(rset);
        verify(rset, times(1)).updateDouble(eq("coordinate_a"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_b"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_c"), anyDouble());
        verify(rset, times(1)).updateInt(eq("coordinate_type"), eq(AbstractCoordinate.CoordinateType.SPHERIC.ordinal()));
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
        assertEquals(790743874, coordinate.hashCode());
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

    @Test
    public void testEqualsSameCoors() {
        final SphericCoordinate sphericCoordinate0 = new SphericCoordinate(0.8288490587889791, 0.8969196083497735, 2.083266665599966);
        final SphericCoordinate sphericCoordinate1 = new SphericCoordinate(0.8288490587889791, 0.8969196083497735, 2.083266665599966);
        assertTrue(sphericCoordinate0.equals(sphericCoordinate1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPhiAsNaN() {
        new SphericCoordinate(Double.NaN, 1.0, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThetaAsNaN() {
        new SphericCoordinate(1.0, Double.NaN, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRadiusAsNaN() {
        new SphericCoordinate(1.0, 1.0, Double.NaN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPhiAsInf() {
        new SphericCoordinate(Double.POSITIVE_INFINITY, 1.0, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThetaAsInf() {
        new SphericCoordinate(1.0, Double.POSITIVE_INFINITY, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorRadiusAsInf() {
        new SphericCoordinate(1.0, 1.0, Double.POSITIVE_INFINITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeRadiusArgs() {
        new SphericCoordinate(1.0, 1.0, -1.0);
    }

}