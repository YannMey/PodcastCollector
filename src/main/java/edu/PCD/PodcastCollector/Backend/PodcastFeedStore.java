package edu.PCD.PodcastCollector.Backend;


import java.net.URL;
import java.util.Collection;

public abstract class PodcastFeedStore {

    public abstract Collection<PodcastFeedEntry> getFeeds();

    public abstract void followPodcastFeed(URL url);
    public abstract void unfollowPodcastFeed(URL url);

    public abstract void addFeed(String url);
}
