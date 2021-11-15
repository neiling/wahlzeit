package org.wahlzeit.model;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BirdPhotoManager extends PhotoManager {

    /**
     *
     */
    protected static final BirdPhotoManager instance = new BirdPhotoManager();

    /**
     *
     */
    public BirdPhotoManager() {
        super();
    }

    /**
     *
     */
    public static BirdPhotoManager getInstance() {
        return instance;
    }

    /**
     *
     */
    @Override
    protected BirdPhoto createObject(ResultSet rset) throws SQLException {
        return BirdPhotoFactory.getInstance().createPhoto(rset);
    }

    /**
     *
     */
    @Override
    public BirdPhoto createPhoto(File file) throws Exception {
        return (BirdPhoto) super.createPhoto(file);
    }


}
