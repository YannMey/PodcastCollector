package edu.PCD.PodcastCollector.Frontend;

import edu.PCD.PodcastCollector.Backend.*;
import edu.PCD.PodcastCollector.Frontend.Controllers.MainPage.BarLectureController;
import edu.PCD.PodcastCollector.Frontend.Controllers.MainPage.DownloadController;
import edu.PCD.PodcastCollector.Frontend.Controllers.MainPage.RecentController;
import edu.PCD.PodcastCollector.Utils.Exception.NoPodcastAvailable;

import java.nio.channels.NotYetConnectedException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Model {
    private Podcast current;
    private final static Model instance = new Model();
    private Boolean toReload;
    private List<Podcast> recommandation_nouveau = new ArrayList<>();
    private List<Podcast> recommandation_replay = new ArrayList<>();
    private PodcastFeedManager podcastFeedManager;
    private BarLectureController lecteur;
    private RecentController recentController;
    private DownloadController setDownloadController;
    public void initData() throws Exception {

        if (getPodcastFeedManager().isConnected()) {
            current = getRandomPodcast();
        } else {
            if (getPodcastFeedManager().getPodcasts().toArray().length != 0) {
                current = getRandomPodcast();
            } else {
                current = null;
            }
        }
        toReload = false;
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Model.getInstance().setToReload(true);
            }
        }, 1, 5, TimeUnit.MINUTES);
    }
    public Set<Category> getAllCategories(){
        Set<Category> set=new HashSet<>();
        for (PodcastFeed podcastFeed :
                getPodcastFeedManager().getFeeds()) {
            Category category= (Category) podcastFeed.getRoot();
            for (Media category1:category.getChildren()){
                if (category1 instanceof Category){
                    set.add((Category) category1);
                }
            }

        }
        return set;
    }
    public void setSetDownloadController(DownloadController setDownloadController) {
        this.setDownloadController = setDownloadController;
    }

    public DownloadController getSetDownloadController() {
        return setDownloadController;
    }

    public void setRecentController(RecentController recentController){
        this.recentController=recentController;
    }
    public void setLecteur(BarLectureController lecteur) {
        this.lecteur = lecteur;
    }

    public BarLectureController getLecteur() {
        return lecteur;
    }

    public void setCurrent(Podcast current) {
        this.current = current;
    }

    public RecentController getRecentController() {
        return recentController;
    }

    public void setToReload(Boolean toReload) {
        this.toReload = toReload;
        if (this.toReload) {
            podcastFeedManager.update();
            System.out.println("Reloading Database in Background");
            this.getRecentController().reinit();
        }
        this.toReload = false;
    }

    public Boolean getToReload() {
        return toReload;
    }

    public static Model getInstance() {
        return instance;
    }


    public void setPodcastFeedManager(PodcastFeedManager podcastFeedManager) {
        this.podcastFeedManager = podcastFeedManager;
    }

    public PodcastFeedManager getPodcastFeedManager() {
        return podcastFeedManager;
    }

    public Podcast getRandomPodcast() throws Exception {
        final Podcast[] current = {null};
        if (podcastFeedManager == null) {
            throw new NotYetConnectedException();
        }
        Collection<Podcast> podcastFeeds = podcastFeedManager.getPodcasts();
        Optional<Podcast> maybeCurrent = podcastFeeds.stream().skip((int) (podcastFeeds.size() * Math.random())).findFirst();
        maybeCurrent.ifPresent(podcast -> current[0] = podcast);
        if (maybeCurrent.isEmpty()) {
            throw new NoPodcastAvailable("The Initialization of the player did not find any podcast");
        }
        return current[0];
    }

    public List<Podcast> getRandomPodcastsIn(Collection<Podcast> podcastFeeds, int number) throws NoPodcastAvailable {
        if (podcastFeeds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Podcast> podcastList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Optional<Podcast> maybeCurrent = podcastFeeds.stream().skip((int) (podcastFeeds.size() * Math.random())).findFirst();

            maybeCurrent.ifPresent(podcastList::add);
            if (maybeCurrent.isEmpty()) {
                throw new NoPodcastAvailable("The Initialization of the player did not find any podcast");
            }
        }
        return podcastList;
    }

    public List<PodcastFeed> getRandomPodcastFeedsIn(Collection<PodcastFeed> podcastFeeds, int number) throws NoPodcastAvailable {
        if (podcastFeeds.isEmpty()) {
            return Collections.emptyList();
        }
        List<PodcastFeed> podcastList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Optional<PodcastFeed> maybeCurrent = podcastFeeds.stream().skip((int) (podcastFeeds.size() * Math.random())).findFirst();
            maybeCurrent.ifPresent(podcastList::add);
            if (maybeCurrent.isEmpty()) {
                throw new NoPodcastAvailable("The Initialization of the player did not find any podcast");
            }
        }
        return podcastList;
    }

    public void setRecommandation_nouveau(List<Podcast> recommandation_nouveau) {
        this.recommandation_nouveau = recommandation_nouveau;
    }

    public List<Podcast> getRecommandation_replay() {
        return recommandation_replay;
    }

    public void setRecommandation_replay(List<Podcast> recommandation_replay) {
        this.recommandation_replay = recommandation_replay;
    }

    public List<Podcast> getRecommandation_nouveau() {
        return recommandation_nouveau;
    }

    public Podcast getCurrent() {
        return current;
    }
}