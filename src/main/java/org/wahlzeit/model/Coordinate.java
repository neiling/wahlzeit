package org.wahlzeit.model;

import org.wahlzeit.services.Persistent;

public interface Coordinate extends Persistent {

    public CartesianCoordinate asCartesianCoordinate();

    public double getCartesianDistance(Coordinate otherCoordinate);

    public SphericCoordinate asSphericCoordinate();

    public double getCentralAngle(Coordinate otherCoordinate);

    public boolean isEqual(Coordinate otherCoordinate);

}
