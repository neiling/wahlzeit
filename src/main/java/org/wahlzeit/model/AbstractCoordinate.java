package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.ErrorStrings;
import org.wahlzeit.utils.InvalidCentralAngleException;
import org.wahlzeit.utils.NegativeHashException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate {

    public static final String SPHERIC_COORDINATE_STRING = "SphericCoordinate object";
    public static final String CARTESIAN_COORDINATE_STRING = "CartesianCoordinate object";
    public static final String COORDINATE_STRING = "Coordinate object";

    protected abstract SphericCoordinate getAsSphericCoordinate();

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();

        final SphericCoordinate sphericCoordinate = getAsSphericCoordinate();

        assertNotNull(sphericCoordinate, SPHERIC_COORDINATE_STRING + " after getAsSphericCoordinate()");
        assertClassInvariants();
        return sphericCoordinate;
    }

    protected abstract CartesianCoordinate getAsCartesianCoordinate();

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();

        final CartesianCoordinate cartesianCoordinate = getAsCartesianCoordinate();

        assertNotNull(cartesianCoordinate, CARTESIAN_COORDINATE_STRING + " after getAsCartesianCoordinate()");
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
        assertNotNull(otherCoordinate, COORDINATE_STRING);

        final boolean res = getIsEqual(otherCoordinate);

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
        assertNotNull(otherCoordinate, COORDINATE_STRING);

        final double distance = doGetCartesianDistance(otherCoordinate);

        assertNonNegative(distance, "Value after doGetCartesianDistance(...)");
        assertClassInvariants();
        return distance;
    }

    protected double doGetCentralAngle(final Coordinate otherCoordinate) {
       return asSphericCoordinate().doGetCentralAngle(otherCoordinate);
    }

    @Override
    public double getCentralAngle(final Coordinate otherCoordinate) {
        assertClassInvariants();
        assertNotNull(otherCoordinate, COORDINATE_STRING);

        final double angle = doGetCentralAngle(otherCoordinate);

        assertIsValidCenterAngle(angle, ErrorStrings.INVALID_CENTRAL_ANGEL);
        assertClassInvariants();
        return angle;
    }

    protected abstract void doReadFrom(ResultSet rset) throws SQLException;

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertNotNull(rset, ErrorStrings.RSET_SHOULD_NOT_NULL);

        doReadFrom(rset);

        assertClassInvariants();
    }

    protected abstract void doWriteOn(ResultSet rset) throws SQLException;

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertNotNull(rset, ErrorStrings.RSET_SHOULD_NOT_NULL);

        doWriteOn(rset);

        assertClassInvariants();
    }

    @Override
    public void writeId(final PreparedStatement stmt, final int pos) {
        throw new UnsupportedOperationException(ErrorStrings.COORDINATE_HAS_NO_ID);
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException(ErrorStrings.COORDINATE_HAS_NO_ID);
    }

    protected abstract void assertClassInvariants();

    protected void assertScalar(final double scalar, final String msg) {
        assertIsANumber(scalar, msg + ErrorStrings.IS_NOT_A_NUM);
        assertNotInfinite(scalar, msg + ErrorStrings.IS_INFINITE);
    }

    protected void assertNotNull(final Object o, final String msg) {
       if (o == null) {
           throw new NullPointerException(msg + ErrorStrings.SHOULD_NOT_BE_NULL);
       }
    }

    protected void assertNegativeHash(final int i) {
        if (i < 0) {
            throw new NegativeHashException(ErrorStrings.HASH_IS_NOT_POSITIVE);
        }
    }

    protected void assertNonNegative(final double d, final String errorMessage) {
        if (d < 0) {
            throw new IllegalArgumentException(errorMessage + ErrorStrings.IS_NEGATIVE);
        }
    }

    protected void assertIsANumber(final Double d, final String errorMessage) {
        if (d.isNaN()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    protected void assertNotInfinite(final Double d, final String errorMessage) {
        if (d.isInfinite()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    protected void assertIsValidCenterAngle(final double angle, final String errorMessage) {
        if (angle < Math.toRadians(0) || angle > Math.toRadians(180)) {
            throw new InvalidCentralAngleException(errorMessage);
        }
    }
}
