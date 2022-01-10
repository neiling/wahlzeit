package org.wahlzeit.model;

import org.wahlzeit.utils.ErrorStrings;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Spheric coordinates to a location.
 */
@PatternInstance(
        patternName = "Value Object",
        participants = {}
)
public class SphericCoordinate extends AbstractCoordinate {

    private static final String PHI_STRING = "phi";
    private static final String THETA_STRING = "phi";
    private static final String RADIUS_STRING = "radius";

    private final Double phi;
    private final Double theta;
    private final Double radius;

    private static final Map<Integer, SphericCoordinate> coordinateCache = new HashMap<>();

    // The constructor is to be used for testing purposes only. Otherwise, please use getFromCache.
    SphericCoordinate(final Double phi, final Double theta, final Double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
        assertClassInvariants();
    }

    public static SphericCoordinate getFromResultSet(final ResultSet rset) throws SQLException {
        return getFromCache(
                rset.getDouble("coordinate_a"),
                rset.getDouble("coordinate_b"),
                rset.getDouble("coordinate_c")
        );
    }

    public static SphericCoordinate getFromCache(final double a, final double b, final double c) {
        final int key = Objects.hash(a, b, c);
        if (!coordinateCache.containsKey(key)) {
            coordinateCache.put(key, new SphericCoordinate(a, b, c));
        }
        return coordinateCache.get(key);
    }

    @Override
    protected void doWriteOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_a", phi);
        rset.updateDouble("coordinate_b", theta);
        rset.updateDouble("coordinate_c", radius);
        rset.updateInt("coordinate_type", CoordinateType.SPHERIC.ordinal());
    }

    @Override
    protected CartesianCoordinate getAsCartesianCoordinate() {
        final double x = radius * Math.sin(theta) * Math.cos(phi);
        final double y = radius * Math.sin(theta) * Math.sin(phi);
        final double z = radius * Math.cos(theta);
        return CartesianCoordinate.getFromCache(x, y, z);
    }

    @Override
    protected SphericCoordinate getAsSphericCoordinate() {
        return this;
    }

    @PatternInstance(
            patternName = "Template Method",
            participants = {"Abstract Class", "Concrete Class"}
    )
    @Override
    protected double doGetCentralAngle(final Coordinate otherCoordinate) {
        final SphericCoordinate otherSpCoor = otherCoordinate.asSphericCoordinate();

        final double bigPhi1 = Math.toRadians(90) - theta;
        final double bigPhi2 = Math.toRadians(90) - otherSpCoor.getTheta();
        final double delta = Math.abs(phi - otherSpCoor.getPhi());

        final double cosPhiSinDelta = Math.cos(bigPhi2) * Math.sin(delta);
        final double cosPhiSinPhi = Math.cos(bigPhi1) * Math.sin(bigPhi2);
        final double sinPhiCosPhiCosDelta = Math.sin(bigPhi1) * Math.cos(bigPhi2) * Math.cos(delta);
        final double numerator = Math.sqrt(
                Math.pow(cosPhiSinDelta, 2) + Math.pow((cosPhiSinPhi - sinPhiCosPhiCosDelta),2)
        );

        final double sinPhiSinPhi = Math.sin(bigPhi1) * Math.sin(bigPhi2);
        final double cosPhiCosPhiCosDelta = Math.cos(bigPhi1) * Math.cos(bigPhi2) * Math.cos(delta);
        final double denominator = sinPhiSinPhi + cosPhiCosPhiCosDelta;

        return Math.atan(numerator / denominator);
    }

    @Override
    protected void assertClassInvariants() {
        assertScalar(phi, PHI_STRING);
        assertScalar(theta, THETA_STRING);
        assertScalar(radius, RADIUS_STRING);
        assertNonNegative(radius, RADIUS_STRING);
    }

    public Double getPhi() {
        return phi;
    }

    public Double getTheta() {
        return theta;
    }

    public Double getRadius() {
        return radius;
    }

}
