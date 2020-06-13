package edu.PCD.PodcastCollector.Backend;

import java.net.URL;

import edu.PCD.PodcastCollector.Backend.Media;

public class PodcastFeed {
    private URL url;
    private Media root;
    private String name;
    private String provider;
    private boolean followed;

    public PodcastFeed(URL url, Media root, String name, String provider, boolean followed) {
        this.url = url;
        this.root = root;
        this.name = name;
        this.provider = provider;
        this.followed = followed;
    }

    public URL getURL() { return url; }

    public Media getRoot() { return root; }

    public String getName() { return name; }

    public String getProvider() { return provider; }

    public boolean isFollowed() { return followed; }
}
