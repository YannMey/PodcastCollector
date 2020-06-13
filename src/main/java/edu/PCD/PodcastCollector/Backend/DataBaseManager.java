package edu.PCD.PodcastCollector.Backend;

import java.sql.*;

public class DataBaseManager {

    public static Connection connection = null;
    public  static  final String DATABASE_FILENAME = "podcast_collector.sqlite";

    public DataBaseManager (){};

    public static void connect(String fileName) {


        String path = "jdbc:sqlite:/tmp/" + fileName;
        try {
            connection = DriverManager.getConnection(path);
            executeUpdate("CREATE TABLE IF NOT EXISTS feed (url VARCHAR2(4096) PRIMARY KEY , format VARCHAR2(64), followed INTEGER);");
            //Here we just add to the databse whatever feed we wanna use to retrieve podcasts
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_13939.xml','RSS',0);");
            executeUpdate("INSERT INTO feed VALUES('https://www.arteradio.com/xml_sound_serie?seriename=%22AMAZ%C3%94NIA%22,'RSS',0)");

            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_13957.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_10076.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_11921.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_16274.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_14312.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_10467.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://radiofrance-podcast.net/podcast09/rss_12250.xml','RSS',0)");
            executeUpdate("INSERT INTO feed VALUES('http://www.2hdp.fr/2HDP.xml','RSS',0)");


            executeUpdate("CREATE TABLE IF NOT EXISTS favorite (url VARCHAR2(4096) PRIMARY KEY);");

        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String request) {
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(request);

        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static void executeUpdate(String request)  {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(request);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

}

