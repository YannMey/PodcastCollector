package edu.PCD.TestPodcastCollector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.PCD.PodcastCollector.Backend.AudioPodcast;
import edu.PCD.PodcastCollector.Backend.Category;
import edu.PCD.PodcastCollector.Backend.VideoPodcast;
import edu.PCD.PodcastCollector.Backend.Media;
import edu.PCD.PodcastCollector.Backend.Podcast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MediaTest {
    private Podcast p1;
    private Podcast p2;
    private Category c1;
    private Category c2;
    private Podcast p3;
    private Category c3;

    @BeforeEach
    public void initialize() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 30);
        p1 = new AudioPodcast("Podcast1", "", null, calendar.getTime(), null,
            null);
        calendar.set(2019, Calendar.DECEMBER, 16);
        p2 = new VideoPodcast("Podcast2", "", null, calendar.getTime(), null,
            null);
        c1 = new Category("Category1", "");
        c2 = new Category("Category2", "");
        calendar.set(2019, Calendar.DECEMBER, 24);
        p3 = new AudioPodcast("Podcast3", "", null, calendar.getTime(), null,
            null);
        c3 = new Category("Category3", "");
        c1.add(p1);
        c1.add(p2);
        c1.add(c2);
        c2.add(p3);
        c2.add(c3);
    }

    @Test
    public void testGetPodcastsCount() {
        assertEquals(p1.getPodcastsCount(), 1);
        assertEquals(c1.getPodcastsCount(), 3);
        assertEquals(c2.getPodcastsCount(), 1);
        assertEquals(c3.getPodcastsCount(), 0);
    }

    @Test
    public void testGetPodcasts() {
        assertEquals(p1.getPodcasts().size(), 1);
        assertEquals(p1.getPodcasts().toArray()[0], p1);

        assertEquals(c1.getPodcasts().size(), 3);
        assertTrue(c1.getPodcasts().contains(p1));
        assertTrue(c1.getPodcasts().contains(p2));
        assertTrue(c1.getPodcasts().contains(p3));

        assertEquals(c2.getPodcasts().size(), 1);
        assertEquals(c2.getPodcasts().toArray()[0], p3);

        assertEquals(c3.getPodcasts().size(), 0);
    }

    @Test
    public void testSearchMediaByName() {
        assertEquals(p1.searchMediaByName("Podcast2").size(), 0);
        assertEquals(p1.searchMediaByName("Podcast").size(), 1);
        assertEquals(p1.searchMediaByName("Podcast").toArray()[0], p1);

        assertEquals(c1.searchMediaByName("1").size(), 2);
        assertTrue(c1.searchMediaByName("1").contains(p1));
        assertTrue(c1.searchMediaByName("1").contains(c1));

        assertEquals(c2.searchMediaByName("Podcast").size(), 1);
        assertTrue(c2.searchMediaByName("Podcast").contains(p3));
        assertEquals(c2.searchMediaByName("Schmilblick").size(), 0);

        assertEquals(c3.searchMediaByName("Category").size(), 1);
        assertTrue(c3.searchMediaByName("Category").toArray()[0].equals(c3));
        assertEquals(c3.searchMediaByName("Podcast").size(), 0);
    }

    @Test
    public void testSearchPodcastsByName() {
        assertEquals(p1.searchPodcastsByName("Podcast2").size(), 0);
        assertEquals(p1.searchPodcastsByName("Podcast").size(), 1);
        assertEquals(p1.searchPodcastsByName("Podcast").toArray()[0], p1);

        assertEquals(c1.searchPodcastsByName("Podcast").size(), 3);
        assertTrue(c1.searchPodcastsByName("Podcast").contains(p1));
        assertTrue(c1.searchPodcastsByName("Podcast").contains(p2));
        assertTrue(c1.searchPodcastsByName("Podcast").contains(p3));

        assertEquals(c2.searchPodcastsByName("Podcast").size(), 1);
        assertTrue(c2.searchPodcastsByName("Podcast").contains(p3));
        assertEquals(c2.searchPodcastsByName("Schmilblick").size(), 0);

        assertEquals(c3.searchPodcastsByName("Podcast").size(), 0);
    }

    @Test
    public void testGetLatestPodcasts() {
        assertEquals(p1.getLatestPodcasts(-1).size(), 0);
        assertEquals(p1.getLatestPodcasts(0).size(), 0);
        assertEquals(p1.getLatestPodcasts(1).size(), 1);
        assertEquals(p1.getLatestPodcasts(1).toArray()[0], p1);
        assertEquals(p1.getLatestPodcasts(2).size(), 1);
        assertEquals(p1.getLatestPodcasts(2).toArray()[0], p1);

        assertEquals(c1.getLatestPodcasts(-1).size(), 0);
        assertEquals(c1.getLatestPodcasts(0).size(), 0);
        assertEquals(c1.getLatestPodcasts(1).size(), 1);
        assertEquals(c1.getLatestPodcasts(1).toArray()[0], p1);
        assertEquals(c1.getLatestPodcasts(2).size(), 2);
        assertEquals(c1.getLatestPodcasts(2).toArray()[1], p3);
        assertEquals(c1.getLatestPodcasts(3).size(), 3);
        assertEquals(c1.getLatestPodcasts(3).toArray()[2], p2);
        assertEquals(c1.getLatestPodcasts(4).size(), 3);

        assertEquals(c3.getLatestPodcasts(-1).size(), 0);
        assertEquals(c3.getLatestPodcasts(0).size(), 0);
        assertEquals(c3.getLatestPodcasts(1).size(), 0);
    }
}
