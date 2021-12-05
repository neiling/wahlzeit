package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate {

    protected abstract SphericCoordinate getAsSphericCoordinate();

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();

        final SphericCoordinate sphericCoordinate = getAsSphericCoordinate();

        assertNotNull(sphericCoordinate);
        assertClassInvariants();
        return sphericCoordinate;
    }

    protected abstract CartesianCoordinate getAsCartesianCoordinate();

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();

        final CartesianCoordinate cartesianCoordinate = getAsCartesianCoordinate();

        assertNotNull(cartesianCoordinate);
        assertClassInvariants();
        return cartesianCoordinate;
    }

    protected int getHashCode() {
        return asCartesianCoordinate().getHashCode();
    }

    @Override
    public int hashCode() {
        assertClassInvariants();

        final int hashCode = getHashCode();

        assertNegativeHash(hashCode);
        assertClassInvariants();
        return hashCode;
    }

    protected boolean getIsEqual(final Coordinate otherCoordinate) {
        return asCartesianCoordinate().isEqual(otherCoordinate);
    }

    @Override
    public boolean isEqual(final Coordinate otherCoordinate) {
        assertClassInvariants();

        final boolean res = getIsEqual(otherCoordinate);

        assertNotNull(otherCoordinate);
        assertClassInvariants();
        return res;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinate && isEqual((Coordinate) o);
    }

    protected double doGetCartesianDistance(final Coordinate otherCoordinate) {
        return asCartesianCoordinate().doGetCartesianDistance(otherCoordinate);
    }

    @Override
    public double getCartesianDistance(final Coordinate otherCoordinate) {
        assertClassInvariants();
        assertNotNull(otherCoordinate);

        final double distance = doGetCartesianDistance(otherCoordinate);

        assertNonNegative(distance);
        assertClassInvariants();
        return distance;
    }

    protected double doGetCentralAngle(final Coordinate otherCoordinate) {
       return asSphericCoordinate().doGetCentralAngle(otherCoordinate);
    }

    @Override
    public double getCentralAngle(final Coordinate otherCoordinate) {
        assertClassInvariants();
        assertNotNull(otherCoordinate);

        final double angle = doGetCentralAngle(otherCoordinate);

        assertIsValidCenterAngle(angle);
        assertClassInvariants();
        return angle;
    }

    protected abstract void doReadFrom(ResultSet rset) throws SQLException;

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertNotNull(rset);

        doReadFrom(rset);

        assertClassInvariants();
    }

    protected abstract void doWriteOn(ResultSet rset) throws SQLException;

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertNotNull(rset);

        doWriteOn(rset);

        assertClassInvariants();
    }

    @Override
    public void writeId(final PreparedStatement stmt, final int pos) {
        throw new UnsupportedOperationException("Class Coordinate has no Id.");
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("Class Coordinate has no Id.");
    }

    protected abstract void assertClassInvariants();

    protected void assertNotNull(final Object o) {
       if (o == null) {
           throw new NullPointerException();
       }
    }

    protected void assertNegativeHash(final int i) {
        if (i < 0) {
            throw new RuntimeException("Hash should be positive");
        }
    }

    protected void assertNonNegative(final double d) {
        if (d < 0) {
            throw new IllegalArgumentException("Value should be positive");
        }
    }

    protected void assertIsANumber(final Double d) {
        if (d.isNaN()) {
            throw new IllegalArgumentException("Value should be a number");
        }
    }

    protected void assertNotInfinite(final Double d) {
        if (d.isInfinite()) {
            throw new IllegalArgumentException("Value should be not infinite");
        }
    }

    protected void assertIsValidCenterAngle(final double angle) {
        if (angle < Math.toRadians(0) || angle > Math.toRadians(180)) {
            throw new RuntimeException("Valid angel value should be between 0 and 360 degree");
        }
    }
}
