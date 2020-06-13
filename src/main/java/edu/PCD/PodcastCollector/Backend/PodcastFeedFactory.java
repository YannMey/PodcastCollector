package edu.PCD.PodcastCollector.Backend;

import java.net.URL;

public abstract class PodcastFeedFactory{

    public abstract PodcastFeed create(URL url, boolean followed);

}
