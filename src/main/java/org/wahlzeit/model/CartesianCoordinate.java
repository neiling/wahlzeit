package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Cartesian coordinates to a location.
 */
@PatternInstance(
        patternName = "Value Object",
        participants = {}
)
public class CartesianCoordinate extends AbstractCoordinate {

    private static final String X_STRING = "X";
    private static final String Y_STRING = "Y";
    private static final String Z_STRING = "Z";

    private final static double EPSILON = 1e-6; // 0.00001d

    private final Double x;
    private final Double y;
    private final Double z;

    private static final Map<Integer, CartesianCoordinate> coordinateCache = new HashMap<>();

    /**
     * @methodtype constructor
     */
    // The constructor is to be used for testing purposes only. Otherwise, please use getFromCache.
    CartesianCoordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        assertClassInvariants();
    }

    public static CartesianCoordinate getFromResultSet(final ResultSet rset) throws SQLException {
        return getFromCache(
                rset.getDouble("coordinate_a"),
                rset.getDouble("coordinate_b"),
                rset.getDouble("coordinate_c")
        );
    }

    public static CartesianCoordinate getFromCache(final double a, final double b, final double c) {
        final int key = Objects.hash(a, b, c);
        if (!coordinateCache.containsKey(key)) {
            coordinateCache.put(key, new CartesianCoordinate(a, b, c));
        }
        return coordinateCache.get(key);
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
    protected void doWriteOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_a", x);
        rset.updateDouble("coordinate_b", y);
        rset.updateDouble("coordinate_c", z);
        rset.updateInt("coordinate_type", CoordinateType.CARTESIAN.ordinal());
    }

    @Override
    protected int getHashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    @Override
    protected CartesianCoordinate getAsCartesianCoordinate() {
        return this;
    }

    @PatternInstance(
            patternName = "Template Method",
            participants = {"Abstract Class", "Concrete Class"}
    )
    @Override
    protected double doGetCartesianDistance(final Coordinate otherCoordinate) {
        return getDistance(otherCoordinate.asCartesianCoordinate());
    }

    @Override
    protected SphericCoordinate getAsSphericCoordinate() {
        final CartesianCoordinate origin = getFromCache(0.0,0.0,0.0);
        final double radius = origin.getDistance(this);
        if (radius == 0) {
            return SphericCoordinate.getFromCache(0.0, 0.0, 0.0);
        }
        final double phi = Math.atan2(y, x);
        final double theta = Math.acos(z/radius);
        return SphericCoordinate.getFromCache(phi, theta, radius);
    }

    @Override
    public boolean getIsEqual(final Coordinate otherCoordinate) {
        if (otherCoordinate == null) {
            return false;
        }
        if (otherCoordinate == this) {
            return true;
        }
        final CartesianCoordinate otherCartCoor = otherCoordinate.asCartesianCoordinate();
        return isDoubleEqual(otherCartCoor.getX(), x) &&
                isDoubleEqual(otherCartCoor.getY(), y) &&
                isDoubleEqual(otherCartCoor.getZ(), z);
    }

    @Override
    protected void assertClassInvariants() {
        assertScalar(x, X_STRING);
        assertScalar(y, Y_STRING);
        assertScalar(z, Z_STRING);
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
