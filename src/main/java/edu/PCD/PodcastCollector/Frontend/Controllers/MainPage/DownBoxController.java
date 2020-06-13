package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import edu.PCD.PodcastCollector.Backend.Podcast;
import edu.PCD.PodcastCollector.Frontend.Model;
import edu.PCD.PodcastCollector.Utils.Exception.NoPodcastAvailable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DownBoxController {
    @FXML
    private HBox hBoxDownSuggest;
    @FXML
    private Button leftButtonBoxDownSuggest;
    @FXML
    private VBox vBoxDownSuggest;
    @FXML
    private Label labelDownUpSuggest;
    @FXML
    private HBox hBoxDownUpSuggest;
    @FXML
    private Button leftButtonDownUpSuggest;
    @FXML
    private HBox hBoxElementDownUpSuggest;
    @FXML
    private Button rightButtonDownUpSuggest;
    @FXML
    private Label labelDownDownSuggest;
    @FXML
    private HBox hBoxDownDownSuggest;
    @FXML
    private Button leftButtonDownDownSuggest;
    @FXML
    private HBox hBoxElementDownDownSuggest;
    @FXML
    private Button rightButtonDownDownSuggest;
    @FXML
    private Button rightButtonDownBoxSuggest;
    private Collection<Podcast> currentReplay;
    private Collection<Podcast> currentNew;
    private Model model;
    @FXML
    private ElementController element1DownUpSuggestController;
    @FXML
    private ElementController element2DownUpSuggestController;
    @FXML
    private ElementController element3DownUpSuggestController;
    @FXML
    private ElementController element4DownUpSuggestController;
    @FXML
    private ElementController element5DownUpSuggestController;
    @FXML
    private ElementController element1DownDownSuggestController;
    @FXML
    private ElementController element2DownDownSuggestController;
    @FXML
    private ElementController element3DownDownSuggestController;
    @FXML
    private ElementController element4DownDownSuggestController;
    @FXML
    private ElementController element5DownDownSuggestController;

    public void initialize() throws NoPodcastAvailable {
        model = Model.getInstance();
        currentReplay = model.getRandomPodcastsIn(model.getPodcastFeedManager().getLatestPodcasts(1000), 5);
        currentNew = model.getRandomPodcastsIn(model.getPodcastFeedManager().getLatestPodcasts(1000), 5);
        reload();
    }

    private void reload() throws NoPodcastAvailable {

        List<ElementController> upPanes = Arrays.asList(element1DownUpSuggestController, element2DownUpSuggestController, element3DownUpSuggestController, element4DownUpSuggestController, element5DownUpSuggestController);
        List<ElementController> downPanes = Arrays.asList(element1DownDownSuggestController, element2DownDownSuggestController, element3DownDownSuggestController, element4DownDownSuggestController, element5DownDownSuggestController);
        Iterator<ElementController> panesUp = upPanes.iterator();
        Iterator<ElementController> panesDown = downPanes.iterator();
        for (Podcast podcast : currentReplay) {
            panesUp.next().setCurrent(podcast);
        }
        for (Podcast podcast : currentNew) {
            panesDown.next().setCurrent(podcast);
        }
    }

    public void reloadUp() throws NoPodcastAvailable {
        currentReplay = model.getRandomPodcastsIn(model.getPodcastFeedManager().getLatestPodcasts(1000), 5);
        reload();
    }

    public void reloadDown() throws NoPodcastAvailable{
        currentNew = model.getRandomPodcastsIn(model.getPodcastFeedManager().getLatestPodcasts(1000), 5);
        reload();
    }

    public void reloadAll() throws NoPodcastAvailable{
        currentReplay = model.getRandomPodcastsIn(model.getPodcastFeedManager().getLatestPodcasts(1000), 5);
        currentNew = model.getRandomPodcastsIn(model.getPodcastFeedManager().getLatestPodcasts(1000), 5);
        reload();
    }
}

