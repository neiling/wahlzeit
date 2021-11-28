package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Spheric coordinates to a location.
 */
public class SphericCoordinate extends AbstractCoordinate {

    private Double phi;
    private Double theta;
    private Double radius;

    public SphericCoordinate(final Double phi, final Double theta, final Double radius) {
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    public SphericCoordinate(final ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    @Override
    public void readFrom(final ResultSet rset) throws SQLException {
        if (isCoorInit()) {
            incWriteCount();
        }
        phi = rset.getDouble("coordinate_phi");
        theta = rset.getDouble("coordinate_theta");
        radius = rset.getDouble("coordinate_radius");
    }

    private boolean isCoorInit() {
        return !(phi == null && theta == null && radius == null);
    }

    @Override
    public void writeOn(final ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_phi", phi);
        rset.updateDouble("coordinate_theta", theta);
        rset.updateDouble("coordinate_radius", radius);
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        final double x = radius * Math.sin(theta) * Math.cos(phi);
        final double y = radius * Math.sin(theta) * Math.sin(phi);
        final double z = radius * Math.cos(theta);
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(final Coordinate otherCoordinate) {
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
