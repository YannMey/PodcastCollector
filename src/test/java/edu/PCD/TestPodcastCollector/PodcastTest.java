package edu.PCD.TestPodcastCollector;

import edu.PCD.PodcastCollector.Backend.AudioPodcast;
import edu.PCD.PodcastCollector.Backend.Podcast;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PodcastTest {

    @Test
    public void testDownload() throws MalformedURLException {
        URL url = new URL("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
        Podcast p = new AudioPodcast("p1", "", url, null, null,null);
        p.download();
        assertEquals(p.getDownloadedURL().toString(), "file:" + Podcast.DOWNLOAD_DIR + p.getHash());
    }

}
