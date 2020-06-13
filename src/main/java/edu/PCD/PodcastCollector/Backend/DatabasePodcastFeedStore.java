package edu.PCD.PodcastCollector.Backend;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DatabasePodcastFeedStore extends PodcastFeedStore {

    public DatabasePodcastFeedStore(){};

    public Collection<PodcastFeedEntry> getFeeds(){

        Collection<PodcastFeedEntry> listFeeds = new ArrayList<PodcastFeedEntry>();
        ResultSet rs = DataBaseManager.executeQuery("SELECT * FROM feed;");
        try{
            assert rs != null;
            while(rs.next()){
                PodcastFeedEntry podcastFeedEntry = new PodcastFeedEntry(new URL(rs.getString(1)),
                        rs.getString(2), rs.getInt(3) != 0);
                listFeeds.add(podcastFeedEntry);
            }
        }
        catch (SQLException | MalformedURLException e){
            e.printStackTrace();
            return null;
        }

        return listFeeds;
    }
    public void addFeed(String url){
        DataBaseManager.executeUpdate("INSERT INTO feed VALUES('"+url+"','RSS',0);");
    }


    public void followPodcastFeed(URL url) {
        DataBaseManager.executeUpdate("UPDATE feed SET followed=1 WHERE url='"+url.toString()+"';");
    }

    public void unfollowPodcastFeed(URL url) {
        DataBaseManager.executeUpdate("UPDATE feed SET followed=0 WHERE url='"+url.toString()+"';");
    }
}
