package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

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
    }

    /**
     * @methodtype constructor
     */
    public Location(final double x, final double y, final double z) {
        this.coordinate = new Coordinate(x, y, z);
    }

    /**
     * @methodtype constructor
     */
    public Location(final ResultSet rset) throws SQLException {
        this.coordinate = new Coordinate(rset);
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
        coordinate.readFrom(rset);
        if (coordinate.isDirty()) {
            incWriteCount();
        }
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        coordinate.writeOn(rset);
    }

}
