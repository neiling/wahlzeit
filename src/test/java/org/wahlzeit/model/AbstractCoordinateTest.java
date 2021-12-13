package org.wahlzeit.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractCoordinateTest {

    @Test(expected = NullPointerException.class)
    public void testWriteOnRsetNull() throws SQLException {
        CartesianCoordinate coordinate = new CartesianCoordinate(1.0, 1.0, 0.0);
        coordinate.writeOn(null);
    }

    @Test(expected = NullPointerException.class)
    public void testReadFromRsetNull() throws SQLException {
        CartesianCoordinate coordinate = new CartesianCoordinate(1.0, 1.0, 0.0);
        coordinate.readFrom(null);
    }

    @Test
    public void testAsSphericCoordinateAsserts() {
        AbstractCoordinate coordinate = spy(AbstractCoordinate.class);
        when(coordinate.getAsSphericCoordinate()).thenReturn(new SphericCoordinate(1.0, 1.0, 1.0));
        coordinate.asSphericCoordinate();
        verify(coordinate, times(1)).assertNotNull(any(), anyString());
        verify(coordinate, times(2)).assertClassInvariants();
    }

    @Test
    public void testGetCartesianDistanceAsserts() {
        AbstractCoordinate coordinate = spy(AbstractCoordinate.class);
        when(coordinate.getAsCartesianCoordinate()).thenReturn(new CartesianCoordinate(2.0, 1.0, 1.0));
        coordinate.getCartesianDistance(new CartesianCoordinate(1.0, 1.0, 2.0));
        verify(coordinate, times(2)).assertNotNull(any(), anyString());
        verify(coordinate, times(4)).assertClassInvariants();
        verify(coordinate, times(1)).assertNonNegative(anyDouble(), anyString());
    }

    @Test
    public void testGetCentralAngleAsserts() {
        AbstractCoordinate coordinate = spy(AbstractCoordinate.class);
        when(coordinate.getAsSphericCoordinate()).thenReturn(new SphericCoordinate(1.0, 1.0, 1.0));
        coordinate.getCentralAngle(new CartesianCoordinate(1.0, 1.0, 2.0));
        verify(coordinate, times(2)).assertNotNull(any(), anyString());
        verify(coordinate, times(4)).assertClassInvariants();
        verify(coordinate, times(1)).assertIsValidCenterAngle(anyDouble(), anyString());
    }

    @Test
    public void testWriteOnAsserts() throws SQLException {
        AbstractCoordinate coordinate = spy(AbstractCoordinate.class);
        coordinate.writeOn(mock(ResultSet.class));
        verify(coordinate, times(1)).assertNotNull(any(), anyString());
        verify(coordinate, times(2)).assertClassInvariants();
    }

    @Test(expected = RuntimeException.class)
    public void testAssertNegativeHash() {
        AbstractCoordinate coordinate = spy(AbstractCoordinate.class);
        coordinate.assertNegativeHash(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testAssertIsValidCenterAngle() {
        AbstractCoordinate coordinate = spy(AbstractCoordinate.class);
        coordinate.assertIsValidCenterAngle(181, anyString());
    }

}