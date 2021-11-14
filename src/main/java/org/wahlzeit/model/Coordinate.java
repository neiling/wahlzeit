package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * A coordinate to a location.
 * The class is immutable, otherwise it would be possible to change data without
 * saving it to the database. Alternatively it would be possible to extend the
 * class with DataObject. Read the report for more information.
 */
public class Coordinate extends DataObject {

    private final static double EPSILON = 1e-6; // 0.00001d

    private Double x;
    private Double y;
    private Double z;

    /**
     * @methodtype constructor
     */
    Coordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @methodtype constructor
     */
    Coordinate(final ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public double getDistance(final Coordinate coor) {
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

    public boolean isEqual(final Coordinate that) {
        if (that == null)
            return false;
        if (that == this)
            return true;
        return isDoubleEqual(that.getX(), x)&&
            isDoubleEqual(that.getY(), y) &&
            isDoubleEqual(that.getZ(), z);
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("Coordinate has no Id.");
    }

    @Override
    public void writeId(final PreparedStatement stmt, final int pos) {
        throw new UnsupportedOperationException("Coordinate has no Id.");
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
    public boolean equals(final Object o) {
        return o instanceof Coordinate && isEqual((Coordinate) o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
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
