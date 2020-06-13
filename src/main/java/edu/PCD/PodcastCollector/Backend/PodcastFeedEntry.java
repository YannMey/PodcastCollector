package edu.PCD.PodcastCollector.Backend;

import java.net.URL;

public class PodcastFeedEntry {

    private final URL url;
    private final String format;
    private final boolean followed;

    public PodcastFeedEntry(URL url, String format, boolean followed) {
        this.url = url;
        this.format = format;
        this.followed = followed;
    }

    public URL getURL() {
        return url;
    }

    public String getFormat() {
        return format;
    }

    public boolean isFollowed() {
        return followed;
    }
}