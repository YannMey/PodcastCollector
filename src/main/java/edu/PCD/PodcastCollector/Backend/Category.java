package edu.PCD.PodcastCollector.Backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.PCD.PodcastCollector.Backend.Media;
import edu.PCD.PodcastCollector.Backend.Podcast;

public class Category extends Media {
    private Collection<Media> children;

    public Category(String name, String description) {
        super(name, description);

        children = new ArrayList<Media>();
    }

    public void add(Media m) {
        children.add(m);
    }

    public int getPodcastsCount() {
        int count = 0;

        for (Media m : children)
            count += m.getPodcastsCount();

        return count;
    }

    public Collection<Podcast> getPodcasts() {
        Collection<Podcast> collection = new ArrayList<Podcast>();

        for (Media m : children)
            collection.addAll(m.getPodcasts());

        return collection;
    }

    public Collection<Media> searchMediaByName(String name) {
        Collection<Media> collection = new ArrayList<Media>();

        if (getName().toLowerCase().contains(name.toLowerCase()))
            collection.add(this);

        for (Media m : children)
            collection.addAll(m.searchMediaByName(name));

        return collection;
    }

    public Collection<Podcast> searchPodcastsByName(String name) {
        Collection<Podcast> collection = new ArrayList<Podcast>();

        for (Media m : children)
            collection.addAll(m.searchPodcastsByName(name));

        return collection;
    }
    @SuppressWarnings("unchecked")
    public Collection<Podcast> getLatestPodcasts(int n) {
        if (n < 1)
            return Collections.EMPTY_LIST;

        List<Podcast> list = new ArrayList<Podcast>();

        for (Media m : children) {
            list.addAll(m.getLatestPodcasts(n));
        }

        if (list.size() == 0)
            return Collections.EMPTY_LIST;

        list.sort((p1, p2) -> p2.getDate().compareTo(p1.getDate()));

        if (list.size() >= n)
            return (Collection<Podcast>)list.subList(0, n);
        else
            return (Collection<Podcast>)list.subList(0, list.size());
    }
    public Collection<Media> getChildren(){
        return children;
    }
}
