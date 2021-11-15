package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BirdPhoto extends Photo {

    private String species;

    public BirdPhoto() {
        super();
    }

    public BirdPhoto(PhotoId myId) {
        super(myId);
    }

    public BirdPhoto(ResultSet rset) throws SQLException {
        super(rset);
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
        incWriteCount();
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);
        species = rset.getString("bird_species");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        super.writeOn(rset);
        rset.updateString("bird_species", species);
    }

}
