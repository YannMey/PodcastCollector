package edu.PCD.PodcastCollector.Backend;

import edu.PCD.PodcastCollector.Backend.AtomPodcastFeedFactory;
import edu.PCD.PodcastCollector.Backend.PodcastFeedFactory;
import edu.PCD.PodcastCollector.Backend.RSSPodcastFeedFactory;

public class PodcastFeedFactoryProvider {
    public static PodcastFeedFactory getFactory(String format) {
        switch (format) {
        case "RSS":
            return new RSSPodcastFeedFactory();
        case "ATOM":
            return new AtomPodcastFeedFactory();
        default:
            return null;
        }
    }
}
