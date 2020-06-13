package edu.PCD.PodcastCollector.Backend;

import java.math.BigInteger;
import java.net.URL;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.File;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

// TODO
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

import edu.PCD.PodcastCollector.Backend.Media;

public abstract class Podcast extends Media implements Serializable {

    public static final String DOWNLOAD_DIR = "/tmp/podcasts/";

    private URL url;
    private Date date;
    private Duration duration;
    private URL imageURL;
    private boolean downloaded;

    public Podcast(String name, String description, URL url, Date date,
                   Duration duration, URL imageURL) {
        super(name, description);

        this.url = url;
        this.date = date;
        this.duration = duration;
        this.imageURL = imageURL;
        this.downloaded = false;
    }

    public URL getURL() {
        return url;
    }

    public Date getDate() {
        return date;
    }

    public Duration getDuration() {
        return duration;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public URL getDownloadedURL() {
        if (downloaded) {
            try {
                return new File(DOWNLOAD_DIR + getHash()).toURI().toURL();
            } catch (MalformedURLException e) {
                return null;
            }
        }
        return null;
    }

    public String getHash() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(url.toString().getBytes());
            byte[] digest = md.digest();
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public void download() {
        try {
            new File(DOWNLOAD_DIR).mkdirs();
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(DOWNLOAD_DIR + getHash());
            FileChannel fc = fos.getChannel();
            fc.transferFrom(rbc, 0, Long.MAX_VALUE);

            fos.close();

            fos = new FileOutputStream(DOWNLOAD_DIR + getHash() + ".bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);

            fos.close();

            downloaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPodcastsCount() {
        return 1;
    }

    public Collection<Podcast> getPodcasts() {
        return Collections.singletonList(this);
    }

    @SuppressWarnings("unchecked")
    public Collection<Media> searchMediaByName(String name) {
        if (getName().toLowerCase().contains(name.toLowerCase()))
            return Collections.singletonList(this);
        else

            return Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Collection<Podcast> searchPodcastsByName(String name) {
        if (getName().toLowerCase().contains(name.toLowerCase()))
            return Collections.singletonList(this);
        else
            return Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Collection<Podcast> getLatestPodcasts(int n) {
        if (n >= 1)
            return Collections.singletonList(this);
        else
            return Collections.EMPTY_LIST;
    }
}
