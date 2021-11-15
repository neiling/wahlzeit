package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BirdPhotoFactory extends PhotoFactory {

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    private static BirdPhotoFactory instance = null;

    /**
     *
     */
    public BirdPhotoFactory() {
        super();
    }

    /**
     * Public singleton access method.
     */
    public static synchronized BirdPhotoFactory getInstance() {
        if (instance == null) {
            SysLog.logSysInfo("setting BirdPhotoFactory");
            setInstance(new BirdPhotoFactory());
        }

        return instance;
    }

    /**
     * Method to set the singleton instance of PhotoFactory.
     */
    protected static synchronized void setInstance(BirdPhotoFactory photoFactory) {
        if (instance != null) {
            throw new IllegalStateException("attempt to initialize BirdPhotoFactory twice");
        }

        instance = photoFactory;
    }

    /**
     * @methodtype factory
     */
    @Override
    public BirdPhoto createPhoto() {
        return new BirdPhoto();
    }

    /**
     * @methodtype factory
     */
    @Override
    public BirdPhoto createPhoto(PhotoId id) {
        return new BirdPhoto(id);
    }

    /**
     * @methodtype factory
     */
    @Override
    public BirdPhoto createPhoto(ResultSet rset) throws SQLException {
        return new BirdPhoto(rset);
    }

}
