package org.wahlzeit.model;

import java.util.Objects;
/**
 * A Location to a photo.
 * The class is immutable, otherwise it would be possible to change data without
 * saving it to the database. Alternatively it would be possible to extend the
 * class with DataObject. Read the report for more information.
 */
public class Location {

    private final Coordinate coordinate;

    /**
     * @methodtype constructor
     */
    public Location(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * @methodtype constructor
     */
    public Location(final double x, final double y, final double z) {
        this.coordinate = new Coordinate(x, y, z);
    }

    /**
     * @methodtype get
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location))
          return false;
        if (this == o)
          return true;
        Location that = (Location) o;
        return this.coordinate.isEqual(that.getCoordinate());
    }

}
