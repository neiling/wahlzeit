package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;

public abstract class AbstractCoordinate extends DataObject implements Coordinate {

    @Override
    public int hashCode() {
        return asCartesianCoordinate().hashCode();
    }

    @Override
    public boolean isEqual(final Coordinate otherCoordinate) {
        return asCartesianCoordinate().isEqual(otherCoordinate);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinate && isEqual((Coordinate) o);
    }

    @Override
    public double getCartesianDistance(final Coordinate otherCoordinate) {
        return asCartesianCoordinate().getCartesianDistance(otherCoordinate);
    }

    @Override
    public double getCentralAngle(final Coordinate otherCoordinate) {
        return asSphericCoordinate().getCentralAngle(otherCoordinate);
    }

    @Override
    public void writeId(final PreparedStatement stmt, final int pos) {
        throw new UnsupportedOperationException("Class Coordinate has no Id.");
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("Class Coordinate has no Id.");
    }

}
