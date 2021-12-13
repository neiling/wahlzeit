package org.wahlzeit.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.imageio.IIOException;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BirdPhotoManagerTest {

    // This test at the moment are not run able, because of the Singleton Pattern initialization
    // @Mock
    // private ResultSet rset;

    // @Test
    // public void testCreateObject() throws SQLException {
    //     when(rset.getString("owner_email_address")).thenReturn("root@localhost");
    //     when(rset.getString("owner_home_page")).thenReturn("http://wahlzeit.org/filter?userName=admin");
    //     when(rset.getString(eq("bird_species"))).thenReturn("Great tit");
    //     final BirdPhoto photo = BirdPhotoManager.getInstance().createObject(rset);
    //     assertEquals("Great tit", photo.getSpecies());
    // }

    @Test(expected = IIOException.class)
    public void testCreatePhoto() throws Exception {
        BirdPhotoManager.getInstance().createPhoto(new File(""));
    }

}