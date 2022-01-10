package org.wahlzeit.model;

import org.wahlzeit.services.SysLog;
import org.wahlzeit.utils.PatternInstance;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

@PatternInstance(
        patternName = "Singleton", participants = {}
)
public class BirdPhotoManager extends PhotoManager {

    private static boolean isInitialized = false;

    /**
     *
     */
    public BirdPhotoManager() {
        super();
    }

    /**
     * Public singleton access method.
     */
    public static synchronized BirdPhotoManager getInstance() {
        if (!isInitialized) {
            SysLog.logSysInfo("setting BirdPhotoFactory");
            PhotoManager.setInstance(new BirdPhotoManager());
            isInitialized = true;
        }

        return (BirdPhotoManager) PhotoManager.getInstance();
    }

    /**
     * Hidden singleton instance; needs to be initialized from the outside.
     */
    public static void initialize() {
        getInstance(); // drops result due to getInstance() side-effects
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
