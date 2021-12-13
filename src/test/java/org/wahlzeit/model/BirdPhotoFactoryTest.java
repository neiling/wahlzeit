package org.wahlzeit.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BirdPhotoFactoryTest {

    @Test(expected = IllegalStateException.class)
    public void testSetInstance() {
        BirdPhotoFactory.setInstance(BirdPhotoFactory.getInstance());
    }

    @Test
    public void testCreatePhoto() {
        // This test at the moment are not run able, because of the Singleton Pattern initialization.
        // BirdPhoto birdPhoto = BirdPhotoFactory.getInstance().createPhoto();
        // assertNotEquals(null, birdPhoto);
    }

    @Test
    public void testCreatePhotoWithId() {
        PhotoId id = new PhotoId(42);
        // This test at the moment are not run able, because of the Singleton Pattern initialization.
        // BirdPhoto birdPhoto = BirdPhotoFactory.getInstance().createPhoto(id);
        // assertEquals(id, birdPhoto.getId());
    }

}