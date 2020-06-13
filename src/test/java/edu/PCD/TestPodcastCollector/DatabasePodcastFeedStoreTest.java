package edu.PCD.TestPodcastCollector;

import edu.PCD.PodcastCollector.Backend.DataBaseManager;
import edu.PCD.PodcastCollector.Backend.DatabasePodcastFeedStore;
import edu.PCD.PodcastCollector.Backend.PodcastFeedEntry;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class DatabasePodcastFeedStoreTest {

    @Test
    public void testRetrieveDatabase(){

        DataBaseManager.connect("testDataBaseManager.sqlite");
        DatabasePodcastFeedStore databasePodcastFeedStore = new DatabasePodcastFeedStore();
        Collection<PodcastFeedEntry> feedList = databasePodcastFeedStore.getFeeds();
        assertEquals(feedList.iterator().next().getURL().toString(),"http://radiofrance-podcast.net/podcast09/rss_13939.xml");
    }

    @Test
    public void testFollowUnfollowPodcastFeed(){

        DataBaseManager.connect("testFollowUnfollow.sqlite");
        DatabasePodcastFeedStore databasePodcastFeedStore = new DatabasePodcastFeedStore();
        try {
            databasePodcastFeedStore.followPodcastFeed(new URL("http://radiofrance-podcast.net/podcast09/rss_13939.xml"));
            Collection<PodcastFeedEntry> feedList = databasePodcastFeedStore.getFeeds();
            assertTrue(feedList.iterator().next().isFollowed());
            databasePodcastFeedStore.unfollowPodcastFeed(new URL("http://radiofrance-podcast.net/podcast09/rss_13939.xml"));
            feedList = databasePodcastFeedStore.getFeeds();
            assertFalse(feedList.iterator().next().isFollowed());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFeed(){
        DataBaseManager.connect("testAddFeed.sqlite");
        DatabasePodcastFeedStore databasePodcastFeedStore = new DatabasePodcastFeedStore();
        databasePodcastFeedStore.addFeed("thisIsATestURL");
    }
}
