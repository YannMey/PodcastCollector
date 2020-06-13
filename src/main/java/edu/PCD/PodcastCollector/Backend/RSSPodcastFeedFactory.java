package edu.PCD.PodcastCollector.Backend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class RSSPodcastFeedFactory extends PodcastFeedFactory {
    @SuppressWarnings("unchecked")
    public PodcastFeed create(URL url, boolean followed) {
        SyndFeedInput input = new SyndFeedInput();
        try {
            SyndFeed rss = input.build(new XmlReader(url));

            Category root = new Category(rss.getTitle(), rss.getDescription());
            PodcastFeed feed = new PodcastFeed(url, root, rss.getTitle(), rss.getImage()==null?null:rss.getImage().getUrl(), followed);

            Map<String, Category> map = new HashMap<String, Category>();

            for (SyndEntry entry : (List<SyndEntry>)rss.getEntries()) {
                for (SyndCategory category : (List<SyndCategory>)entry.getCategories()) {
                    URL enclosureURL = null;
                    URL imageURL = null;
                    if (((List<SyndEnclosure>)entry.getEnclosures()).size() > 0)
                        enclosureURL = new URL(((List<SyndEnclosure>)entry.getEnclosures()).get(0).getUrl());
                    if (rss.getImage() != null)
                        imageURL = new URL(rss.getImage().getUrl());
                    Podcast p = new AudioPodcast(
                        entry.getTitle(),
                        entry.getDescription().getValue(),
                        enclosureURL,
                        entry.getPublishedDate(),
                        null,
                        imageURL
                    );
                    if (map.containsKey(category.getName())) {
                        map.get(category.getName()).add(p);
                    } else {
                        Category c = new Category(category.getName(), "");
                        c.add(p);
                        root.add(c);
                        map.put(category.getName(), c);
                    }
                }
            }

            return feed;
        } catch (FeedException | IOException e) {
            return null;
        }
    }
}
