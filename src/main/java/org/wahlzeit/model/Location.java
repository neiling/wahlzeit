package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.model.AbstractCoordinate.CoordinateType;
import org.wahlzeit.utils.ErrorStrings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * A Location to a photo.
 * The class is immutable, otherwise it would be possible to change data without
 * saving it to the database. Alternatively it would be possible to extend the
 * class with DataObject. Read the report for more information.
 */
public class Location extends DataObject {

    private final Coordinate coordinate;

    /**
     * @methodtype constructor
     */
    public Location(final Coordinate coordinate) {
        this.coordinate = coordinate;
        // At this point I assume that a new location has been added by the user and should be stored in the database.
        incWriteCount();
    }

    /**
     * @methodtype constructor
     */
    public Location(final ResultSet rset) throws SQLException {
        switch (CoordinateType.values()[rset.getInt("coordinate_type")]) {
            case CARTESIAN:
                this.coordinate = CartesianCoordinate.getFromResultSet(rset);
                break;
            case SPHERIC:
                this.coordinate = SphericCoordinate.getFromResultSet(rset);
                break;
            default:
                throw new IllegalArgumentException("Unknown coordinate type from database.");
        }
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

    @Override
    public int hashCode() {
        return Objects.hash(getCoordinate());
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("Location has no Id.");
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) {
        throw new UnsupportedOperationException("Location has no Id.");
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        throw new UnsupportedOperationException("Use the constructor Location(ResultSet rset) to create a Location object from database");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        // The coordinate data of a Location should only write to the Database if they come from Userinput
        // This should prevent that by constant alternating conversion of coordinates,
        // for example Spherical to Cartesian, it comes the rounding errors add up.
        if (isDirty()) {
            coordinate.writeOn(rset);
        }
    }

}
