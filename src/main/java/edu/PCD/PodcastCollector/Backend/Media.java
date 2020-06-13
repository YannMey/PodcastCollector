package edu.PCD.PodcastCollector.Backend;

import java.util.Collection;
import java.io.Serializable;

import edu.PCD.PodcastCollector.Backend.Podcast;

public abstract class Media implements Serializable {
    private String name;
    private String description;

    public Media(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public abstract int getPodcastsCount();
    public abstract Collection<Podcast> getPodcasts();
    public abstract Collection<Media> searchMediaByName(String name);
    public abstract Collection<Podcast> searchPodcastsByName(String name);
    public abstract Collection<Podcast> getLatestPodcasts(int n);
}
