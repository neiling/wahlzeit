package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"AbstractProduct"}
)
public class BirdPhoto extends Photo {

    private Bird bird;

    public BirdPhoto() {
        super();
    }

    public BirdPhoto(PhotoId myId) {
        super(myId);
    }

    public BirdPhoto(ResultSet rset) throws SQLException {
        readFrom(rset);
    }

    public Bird getBrid() {
        return bird;
    }

    public void setBird(final Bird bird) {
        this.bird = bird;
        incWriteCount();
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);
        bird = BirdManager.getInstance().createObject(rset);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        super.writeOn(rset);
        if (Objects.nonNull(bird)) {
            bird.writeOn(rset);
        }
    }

}
