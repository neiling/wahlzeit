package org.wahlzeit.model;

import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"AbstractProduct"}
)
/*
---- cw11 ----
Method calls that create a new BirdPhoto object:

1. When the object is created from data from the database:

BirdPhotoManager.getInstance().createPhoto(rset)
BirdPhotoFactory.getInstance().createPhoto(rset)
BirdPhoto(ResultSet rset)
BirdPhoto.readFrom(ResultSet rset)
Photo.readFrom(ResultSet rset)
Location(ResultSet rset)
CartesianCoordinate. or SphericCoordinate.getFromResultSet(ResultSet rset)

2. When the object is created by the user input:

BirdPhotoManager.createObject(File file)
PhotoManager.createObject(File file)
PhotoId.getNextId()
PhotoUtil.createPhoto(File source, PhotoId id)
PhotoFactory.getInstance().createPhoto(PhotoId id)
BirdPhoto.setTags(Tags tags)
BirdPhoto.setLocation(Location location)
BirdPhoto.setBird(Bird bird)

---- cw12 ----
Client-Service Collaboration:
BirdPhoto (Client) -> Bird (Service)
The client (BirdPhoto) binds to service (Bird) class.

The collaboration is responsible for ensuring all domain functionality.
This includes among others:

- Changing object attributes via setter methods
- Accessing object attributes via getter methods
 */
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
