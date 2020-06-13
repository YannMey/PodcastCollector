package edu.PCD.TestPodcastCollector;

import edu.PCD.PodcastCollector.Backend.DataBaseManager;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseManagerTest {


    @Test
    public void testCreateDB(){
        DataBaseManager.connect(DataBaseManager.DATABASE_FILENAME);
        try {
            DatabaseMetaData meta = DataBaseManager.connection.getMetaData();
            ResultSet rs = meta.getTables(null, null, "%", null);
            rs.next();
            assertEquals(rs.getString(3),"favorite");
            rs.next();
            assertEquals(rs.getString(3),"feed");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
