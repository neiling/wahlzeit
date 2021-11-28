package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Cartesian coordinates to a location.
 */
public class CartesianCoordinate extends AbstractCoordinate {

    private final static double EPSILON = 1e-6; // 0.00001d

    private Double x;
    private Double y;
    private Double z;

    /**
     * @methodtype constructor
     */
    CartesianCoordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @methodtype constructor
     */
    CartesianCoordinate(final ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public double getDistance(final CartesianCoordinate coor) {
        return Math.sqrt(
                squareDiff(coor.getX(), x) +
                squareDiff(coor.getY(), y) +
                squareDiff(coor.getZ(), z)
                );
    }

    private static double squareDiff(final double a, final double b) {
        return Math.pow(a - b, 2);
    }

    private static boolean isDoubleEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    @Override
    public void readFrom(final ResultSet rset) throws SQLException {
        if (isCoorInit()) {
            incWriteCount();
        }
        x = rset.getDouble("coordinate_x");
        y = rset.getDouble("coordinate_y");
        z = rset.getDouble("coordinate_z");
    }

    private boolean isCoorInit() {
        return !(x == null && y == null && z == null);
    }

    @Override
    public void writeOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_x", x);
        rset.updateDouble("coordinate_y", y);
        rset.updateDouble("coordinate_z", z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(final Coordinate otherCoordinate) {
        return getDistance(otherCoordinate.asCartesianCoordinate());
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        final CartesianCoordinate origin = new CartesianCoordinate(0.0,0.0,0.0);
        final double radius = origin.getDistance(this);
        if (radius == 0) {
            return new SphericCoordinate(0.0, 0.0, 0.0);
        }
        final double phi = Math.atan2(y, x);
        final double theta = Math.acos(z/radius);
        return new SphericCoordinate(phi, theta, radius);
    }

    @Override
    public boolean isEqual(final Coordinate otherCoordinate) {
        if (otherCoordinate == null) {
            return false;
        }
        if (otherCoordinate == this) {
            return true;
        }
        final CartesianCoordinate otherCartCoor = otherCoordinate.asCartesianCoordinate();
        return isDoubleEqual(otherCartCoor.getX(), x)&&
                isDoubleEqual(otherCartCoor.getY(), y) &&
                isDoubleEqual(otherCartCoor.getZ(), z);
    }

    /**
     * @methodtype get
     */
    public double getX() {
        return x;
    }

    /**
     * @methodtype get
     */
    public double getY() {
        return y;
    }

    /**
     * @methodtype get
     */
    public double getZ() {
        return z;
    }

}
