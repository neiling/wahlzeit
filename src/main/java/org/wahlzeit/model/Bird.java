package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bird extends DataObject {

    private String species;
    private BirdType birdType;

    public Bird(final ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public Bird(final String species, final BirdType birdType) {
        this.species = species;
        this.birdType = birdType;
    }

    public BirdType getBirdType() {
        return birdType;
    }

    public String getSpecies() {
        return species;
    }

    @Override
    public void readFrom(final ResultSet rset) throws SQLException {
        species = rset.getString("bird_species");
        birdType = BirdManager.getInstance().getOrCreateBirdType(rset.getString("birdtype_name"));
    }

    @Override
    public void writeOn(final ResultSet rset) throws SQLException {
        rset.updateString("birdtype_name", birdType.getName());
        rset.updateString("bird_species", species);
    }

    @Override
    public String getIdAsString() {
        throw new UnsupportedOperationException("Bird has no Id.");
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        throw new UnsupportedOperationException("Bird has no Id.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bird bird = (Bird) o;

        if (getSpecies() != null ? !getSpecies().equals(bird.getSpecies()) : bird.getSpecies() != null) return false;
        return getBirdType() != null ? getBirdType().equals(bird.getBirdType()) : bird.getBirdType() == null;
    }

    @Override
    public int hashCode() {
        int result = getSpecies() != null ? getSpecies().hashCode() : 0;
        result = 31 * result + (getBirdType() != null ? getBirdType().hashCode() : 0);
        return result;
    }

}
