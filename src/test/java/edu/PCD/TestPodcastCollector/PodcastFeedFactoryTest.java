package edu.PCD.TestPodcastCollector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import edu.PCD.PodcastCollector.Backend.AudioPodcast;
import edu.PCD.PodcastCollector.Backend.Category;
import edu.PCD.PodcastCollector.Backend.Media;
import edu.PCD.PodcastCollector.Backend.Podcast;
import edu.PCD.PodcastCollector.Backend.PodcastFeed;
import edu.PCD.PodcastCollector.Backend.PodcastFeedFactory;
import edu.PCD.PodcastCollector.Backend.RSSPodcastFeedFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PodcastFeedFactoryTest {
    private PodcastFeedFactory rssFactory;
    private PodcastFeed p;

    @BeforeEach
    public void initialize() throws MalformedURLException {
        rssFactory = new RSSPodcastFeedFactory();
        p = rssFactory.create(new URL("https://www.feedforall.com/sample.xml"), false);
    }

    @Test
    public void testGetRoot() {
        assertNotNull(p);
        assertNotNull(p.getRoot());

        assertEquals(p.getName(), "FeedForAll Sample Feed");
        // assertEquals(p.getProvider(), "marketing@feedforall.com");

        assertTrue(p.getRoot() instanceof Category);
        assertEquals(p.getRoot().getName(), "FeedForAll Sample Feed");
        assertEquals(p.getRoot().getDescription(), "RSS is a fascinating technology. The uses for RSS are expanding daily. Take a closer look at how various industries are using the benefits of RSS in their businesses.");

        assertEquals(p.getRoot().getPodcastsCount(), 9);
        assertEquals(p.getRoot().searchPodcastsByName("RSS Solutions for Restaurants").size(), 1);
        assertTrue(p.getRoot().searchPodcastsByName("RSS Solutions for Restaurants").iterator().next().getDescription().contains("Let your customers know the latest specials or events."));
    }
}
