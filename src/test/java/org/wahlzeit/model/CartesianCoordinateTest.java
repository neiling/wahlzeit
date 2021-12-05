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
public class CartesianCoordinateTest {

    @Mock
    private ResultSet rset;

    @Test
    public void testGetCartesianDistanceDiffCoor() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertEquals(1.4177446878757824, cartesianCoordinate.getCartesianDistance(new CartesianCoordinate(0.0, 2.1, 3.1)), 0);
    }

    @Test
    public void testGetCartesianDistanceSameCoor() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertEquals(0, cartesianCoordinate.getCartesianDistance(new CartesianCoordinate(0.1, 1.1, 2.1)), 0);
    }

    @Test
    public void testIsEqualExpectFalse() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertFalse(cartesianCoordinate.isEqual(new CartesianCoordinate(0.1, 1.1, 2.2)));
    }

    @Test
    public void testIsEqualExpectTrue() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertTrue(cartesianCoordinate.isEqual(new CartesianCoordinate(0.1, 1.1, 2.1)));
    }

    @Test
    public void testIsEqualExpectTrueWithEpsilon() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.7999999999999999, 1.1, 2.1);
        assertTrue(cartesianCoordinate.isEqual(new CartesianCoordinate(0.8, 1.1, 2.1)));
    }

    @Test
    public void testIsEqualSameObj() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertTrue(cartesianCoordinate.isEqual(cartesianCoordinate));
    }

    @Test(expected = NullPointerException.class)
    public void testIsEqualNullObj() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        cartesianCoordinate.isEqual(null);
    }

    @Test
    public void testEqualsNullObj() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertFalse(cartesianCoordinate.equals(null));
    }

    @Test
    public void testGetIsEqualsNullObj() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertFalse(cartesianCoordinate.getIsEqual(null));
    }

    @Test
    public void testGetX() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertEquals(0.1, cartesianCoordinate.getX(), 0.0);
    }

    @Test
    public void testGetY() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertEquals(1.1, cartesianCoordinate.getY(), 0.0);
    }

    @Test
    public void testGetZ() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0.1, 1.1, 2.1);
        assertEquals(2.1, cartesianCoordinate.getZ(), 0.0);
    }

    @Test
    public void testReadFrom() throws SQLException {
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.3);
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(rset);
        assertEquals(cartesianCoordinate, new CartesianCoordinate(1.1, 1.2, 1.3));
    }

    @Test
    public void testReadFromTwice() throws SQLException {
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.3);
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(rset);
        assertFalse(cartesianCoordinate.isDirty());
        when(rset.getDouble(eq("coordinate_x"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_y"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_z"))).thenReturn(1.4);
        cartesianCoordinate.readFrom(rset);
        assertEquals(cartesianCoordinate, new CartesianCoordinate(1.1, 1.2, 1.4));
        assertTrue(cartesianCoordinate.isDirty());
    }

    @Test
    public void testWriteOn() throws SQLException {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1, 1.2, 1.3);
        cartesianCoordinate.writeOn(rset);
        verify(rset, times(1)).updateDouble(eq("coordinate_x"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_y"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_z"), anyDouble());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetIdAsString() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1, 1.2, 1.3);
        cartesianCoordinate.getIdAsString();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testWriteId() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1, 1.2, 1.3);
        cartesianCoordinate.writeId(null, 0);
    }

    @Test
    public void testHashCode() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1, 1.2, 1.3);
        assertEquals(719355811, cartesianCoordinate.hashCode());
    }

    @Test
    public void testAsSphericCoordinate() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1, 1.2, 1.3);
        final SphericCoordinate sphericCoordinate = cartesianCoordinate.asSphericCoordinate();
        final double DELTA = 1e-6; // 0.00001d
        assertEquals(0.8288490587889791, sphericCoordinate.getPhi(), DELTA);
        assertEquals(0.8969196083497735, sphericCoordinate.getTheta(), DELTA);
        assertEquals(2.083266665599966, sphericCoordinate.getRadius(), DELTA);
    }

    @Test
    public void testAsSphericCoordinate_IfOrigin() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(0, 0, 0);
        final SphericCoordinate sphericCoordinate = cartesianCoordinate.asSphericCoordinate();
        assertEquals(0, sphericCoordinate.getPhi(), 0);
        assertEquals(0, sphericCoordinate.getTheta(), 0);
        assertEquals(0, sphericCoordinate.getRadius(), 0);
    }

    @Test
    public void testGetCentralAngle() {
        final CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(1.1, 1.2, 1.3);
        final SphericCoordinate sphericCoordinate =
                new SphericCoordinate(0.9, 0.9, 1.0);
        assertEquals(0.055746812345190744, cartesianCoordinate.getCentralAngle(sphericCoordinate), 0);
    }

}
