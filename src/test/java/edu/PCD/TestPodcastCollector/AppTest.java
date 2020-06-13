package edu.PCD.TestPodcastCollector;

import edu.PCD.PodcastCollector.Frontend.MainApp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {
    private final MainApp main = new MainApp();

    @Test
    public void testMain() {
        assertNotNull(main.toString(), "App should have a greeting");
    }
}
