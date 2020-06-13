package edu.PCD.PodcastCollector.Backend;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PodcastFeedManager {
    public static final String DOWNLOAD_DIR = "/tmp/podcasts";

    private PodcastFeedStore store;
    private Collection<PodcastFeed> feeds;

    private boolean connected = false;

    private void setConnected() {
        URL url;
        try {
            url = new URL("https://univ-lorraine.fr/");
            final URLConnection conn = url.openConnection();
            conn.connect();
            connected = true;
        } catch (IOException e) {
            connected = false;
        }
    }

    public boolean isConnected() { return connected; }

    public PodcastFeedManager(PodcastFeedStore store) {
        this.store = store;

        feeds = new ArrayList<
                PodcastFeed>();
    }

    public void add(String url) {
        store.addFeed(url);
    }

    public void update() {
        createDownloadDir();

        setConnected();

        feeds.clear();

        if (connected) {
            for (PodcastFeedEntry e : store.getFeeds()) {
                PodcastFeedFactory factory = PodcastFeedFactoryProvider.getFactory(e.getFormat());
                assert factory != null;
                PodcastFeed feed = factory.create(e.getURL(), e.isFollowed());
                if (feed != null) {
                    feeds.add(feed);
                }
            }
        } else {
                Category root = new Category("Hors-ligne", "Vos podcasts hors-ligne");
                PodcastFeed feed = new PodcastFeed(null, root, "Hors-ligne", "PodcastCollector", true);
                feeds.add(feed);
        }

        updateDownloaded();
    }

    public void followPodcastFeed(PodcastFeed feed) {
        store.followPodcastFeed(feed.getURL());
        update();
    }

    public void unfollowPodcastFeed(PodcastFeed feed) {
        store.unfollowPodcastFeed(feed.getURL());
        update();
    }

    public Collection<PodcastFeed> getFeeds() {
        return feeds;
    }

    public int getPodcastsCount() {
        int count = 0;

        for (PodcastFeed feed : feeds)
            count += feed.getRoot().getPodcastsCount();

        return count;
    }

    public Collection<Podcast> getPodcasts() {
        Collection<Podcast> collection = new ArrayList<Podcast>();

        for (PodcastFeed feed : feeds)
            collection.addAll(feed.getRoot().getPodcasts());

        return collection;
    }

    public Collection<Media> searchMediaByName(String name) {
        Collection<Media> collection = new ArrayList<Media>();

        for (PodcastFeed feed : feeds)
            collection.addAll(feed.getRoot().searchMediaByName(name));

        return collection;
    }

    public Collection<Podcast> searchPodcastsByName(String name) {
        Collection<Podcast> collection = new ArrayList<Podcast>();

        for (PodcastFeed feed : feeds)
            collection.addAll(feed.getRoot().searchPodcastsByName(name));

        return collection;
    }
    @SuppressWarnings("unchecked")
    public Collection<Podcast> getLatestPodcasts(int n) {
        if (n < 1)
            return (Collection<Podcast>)Collections.EMPTY_LIST;

        List<Podcast> list = new ArrayList<Podcast>();

        for (PodcastFeed feed : feeds)
            list.addAll(feed.getRoot().getLatestPodcasts(n));

        if (list.size() == 0)
            return (Collection<Podcast>)Collections.EMPTY_LIST;

        list.sort((p1, p2) -> (p2.getDate().compareTo(p1.getDate())));

        if (list.size() >= n)
            return list.subList(0, n);
        else
            return list.subList(0, list.size());
    }

    public Collection<Podcast> getDownloadedPodcasts() {
        Collection<Podcast> collection = new ArrayList<Podcast>();

        for (Podcast p : getPodcasts()) {
            if (p.isDownloaded())
                collection.add(p);
        }

        return collection;
    }

    private void createDownloadDir() {
        new File(DOWNLOAD_DIR).mkdirs();
    }

    private void updateDownloaded() {
        File[] files = new File(DOWNLOAD_DIR).listFiles();

        assert files != null;
        for (File file : files) {
          try {
            if (file.toURI().toURL().toString().endsWith(".bin")) { continue; }
          } catch (MalformedURLException e) {
              continue;
          }

          if (file.isFile()) {
            if (connected) {
                for (Podcast p : getPodcasts()) {
                    if (file.getName().equals(p.getHash())) {
                        p.setDownloaded(true);
                        break;
                    }
                }
            } else {
                try {
                    File fileBin = new File(file.toURI().toURL().toString().replace("file:", "") + ".bin");
                    FileInputStream fis = new FileInputStream(fileBin);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    AudioPodcast p = (AudioPodcast)ois.readObject();
                    p.setDownloaded(true);
                    ((Category)feeds.iterator().next().getRoot()).add(p);
                } catch(IOException | ClassNotFoundException e) {
                }
            }
          }
        }
    }
}
