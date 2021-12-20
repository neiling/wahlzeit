package org.wahlzeit.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.utils.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PhotoTest {

    @Mock
    private ResultSet rset;

    private Photo initPhotoMockFromDB(final ResultSet rset) throws SQLException {
        when(rset.getInt(eq("id"))).thenReturn(1);

        when(rset.getInt("owner_id")).thenReturn(1);
        when(rset.getString("owner_name")).thenReturn("admin");

        when(rset.getBoolean("owner_notify_about_praise")).thenReturn(false);
        when(rset.getString("owner_email_address")).thenReturn("root@localhost");
        when(rset.getInt("owner_language")).thenReturn(0);
        when(rset.getString("owner_home_page")).thenReturn("http://wahlzeit.org/filter?userName=admin");

        when(rset.getInt("width")).thenReturn(4160);
        when(rset.getInt("height")).thenReturn(5200);

        when(rset.getString("tags")).thenReturn("cat");

        when(rset.getInt("status")).thenReturn(8);
        when(rset.getInt("praise_sum")).thenReturn(10);
        when(rset.getInt("no_votes")).thenReturn(1);

        when(rset.getLong("creation_time")).thenReturn(1636193639987L);

        when(rset.getDouble(eq("coordinate_a"))).thenReturn(1.1);
        when(rset.getDouble(eq("coordinate_b"))).thenReturn(1.2);
        when(rset.getDouble(eq("coordinate_c"))).thenReturn(1.3);
        when(rset.getInt(eq("coordinate_type"))).thenReturn(AbstractCoordinate.CoordinateType.CARTESIAN.ordinal());
        return new Photo(rset);
    }

    @Test
    public void testReadFrom() throws SQLException {
        final Photo photo = initPhotoMockFromDB(rset);
        assertEquals(new PhotoId(1), photo.getId());
        assertEquals(1, photo.getOwnerId());
        assertEquals("admin", photo.getOwnerName());
        assertFalse(photo.getOwnerNotifyAboutPraise());
        assertEquals(EmailAddress.getFromString("root@localhost"), photo.getOwnerEmailAddress());
        assertEquals(Language.ENGLISH, photo.getOwnerLanguage());
        assertEquals(StringUtil.asUrl("http://wahlzeit.org/filter?userName=admin"), photo.getOwnerHomePage());
        assertEquals(4160, photo.getWidth());
        assertEquals(5200, photo.getHeight());
        assertEquals(new Tags("cat"), photo.getTags());
        assertEquals(PhotoStatus.getFromInt(8), photo.getStatus());
        assertEquals(1636193639987L, photo.getCreationTime());
        assertTrue(photo.getLocation().isPresent());
        assertEquals(new Location(new CartesianCoordinate(1.1, 1.2, 1.3)), photo.getLocation().get());
    }

    @Test
    public void testReadFromInvalidCoordinate() throws SQLException {
        when(rset.getString("owner_email_address")).thenReturn("root@localhost");
        when(rset.getString("owner_home_page")).thenReturn("http://wahlzeit.org/filter?userName=admin");
        when(rset.getDouble(eq("coordinate_a"))).thenReturn(Double.NaN);
        final Photo photo = new Photo(rset);
        assertTrue(photo.getLocation().isPresent());
        assertEquals(new Location(new CartesianCoordinate(0.0, 0.0, 0.0)), photo.getLocation().get());
    }

    @Test(expected = NullPointerException.class)
    public void test_writeOn_empty_photo_exp_NullPointer() throws SQLException {
        final Photo photo = new Photo();
        photo.writeOn(rset);
    }

    @Test
    public void testWriteOn() throws SQLException {
        final Photo photo = initPhotoMockFromDB(rset);
        photo.setLocation(new Location(new CartesianCoordinate(1.1, 1.2, 1.42)));
        photo.writeOn(rset);
        verify(rset, times(1)).updateInt(eq("id"), anyInt());
        verify(rset, times(1)).updateInt(eq("owner_id"), anyInt());
        verify(rset, times(1)).updateString(eq("owner_name"), anyString());
        verify(rset, times(1)).updateBoolean(eq("owner_notify_about_praise"), anyBoolean());
        verify(rset, times(1)).updateString(eq("owner_email_address"), anyString());
        verify(rset, times(1)).updateInt(eq("owner_language"), anyInt());
        verify(rset, times(1)).updateString(eq("owner_home_page"), anyString());
        verify(rset, times(1)).updateInt(eq("width"), anyInt());
        verify(rset, times(1)).updateInt(eq("height"), anyInt());
        verify(rset, times(1)).updateString(eq("tags"), anyString());
        verify(rset, times(1)).updateInt(eq("status"), anyInt());
        verify(rset, times(1)).updateInt(eq("praise_sum"), anyInt());
        verify(rset, times(1)).updateInt(eq("no_votes"), anyInt());
        verify(rset, times(1)).updateLong(eq("creation_time"), anyLong());
        verify(rset, times(1)).updateDouble(eq("coordinate_a"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_b"), anyDouble());
        verify(rset, times(1)).updateDouble(eq("coordinate_c"), anyDouble());
        verify(rset, times(1)).updateInt(eq("coordinate_type"), eq(AbstractCoordinate.CoordinateType.CARTESIAN.ordinal()));
    }

    @Test
    public void testGetLocation() {
        final Photo photo = new Photo();
        assertTrue(photo.getLocation().isEmpty());
    }

    @Test
    public void testSetLocation() throws SQLException {
        final Photo photo = initPhotoMockFromDB(rset);
        photo.setLocation(new Location(new CartesianCoordinate(0.1, 0.2, 0.3)));
        assertTrue(photo.getLocation().isPresent());
        assertEquals(photo.getLocation(), Optional.of(new Location(new CartesianCoordinate(0.1, 0.2, 0.3))));
        assertEquals(photo.getLocation().get(), new Location(new CartesianCoordinate(0.1, 0.2, 0.3)));
        assertTrue(photo.isDirty());
    }

}
