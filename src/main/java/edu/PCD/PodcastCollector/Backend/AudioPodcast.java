package edu.PCD.PodcastCollector.Backend;

import java.net.URL;
import java.time.Duration;
import java.util.Date;

import edu.PCD.PodcastCollector.Backend.Podcast;

public class AudioPodcast extends Podcast {
    public AudioPodcast(String name, String description, URL url, Date date,
        Duration duration, URL imageURL) {
        super(name, description, url, date, duration, imageURL);
    }
}
