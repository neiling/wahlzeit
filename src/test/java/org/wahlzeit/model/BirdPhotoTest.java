package org.wahlzeit.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BirdPhotoTest {

    @Mock
    private ResultSet rset;

    private BirdPhoto initPhotoMockFromDB(final ResultSet rset) throws SQLException {
        when(rset.getString("owner_email_address")).thenReturn("root@localhost");
        when(rset.getString("owner_home_page")).thenReturn("http://wahlzeit.org/filter?userName=admin");
        when(rset.getString(eq("bird_species"))).thenReturn("Great tit");
        return new BirdPhoto(rset);
    }

    @Test
    public void testSetSpecies() throws SQLException {
        final BirdPhoto birdPhoto = initPhotoMockFromDB(rset);
        birdPhoto.setSpecies("Great tit");
        assertTrue(birdPhoto.isDirty());
        assertEquals("Great tit", birdPhoto.getSpecies());
    }

    @Test
    public void testReadFrom() throws SQLException {
        final BirdPhoto birdPhoto = initPhotoMockFromDB(rset);
        assertEquals("Great tit", birdPhoto.getSpecies());
    }


    @Test
    public void testWriteOn() throws SQLException {
        final BirdPhoto birdPhoto = initPhotoMockFromDB(rset);
        birdPhoto.writeOn(rset);
        verify(rset, times(1)).updateString(eq("owner_email_address"), anyString());
        verify(rset, times(1)).updateString(eq("owner_home_page"), anyString());
        verify(rset, times(1)).updateString(eq("bird_species"), anyString());
    }

}