package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;
import org.wahlzeit.utils.PatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Abstract Factory",
        participants = {"ConcreteFactory"}
)
@PatternInstance(
        patternName = "Singleton", participants = {}
)
public class BirdPhotoFactory extends PhotoFactory {

    private static boolean isInitialized = false;

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
        if (!isInitialized) {
            SysLog.logSysInfo("setting BirdPhotoFactory");
            PhotoFactory.setInstance(new BirdPhotoFactory());
            isInitialized = true;
        }

        return (BirdPhotoFactory) PhotoFactory.getInstance();
    }

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    public static void initialize() {
        getInstance(); // drops result due to getInstance() side-effects
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
