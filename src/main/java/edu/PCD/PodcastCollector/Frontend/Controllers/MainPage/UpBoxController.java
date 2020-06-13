package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import edu.PCD.PodcastCollector.Backend.PodcastFeed;
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

public class UpBoxController {
    @FXML
    private HBox hBoxUpSuggest;
    @FXML
    private Button leftButtonBoxUpSuggest;
    @FXML
    private VBox vBoxUpSuggest;
    @FXML
    private Label labelUpUpSuggest;
    @FXML
    private HBox hBoxUpUpSuggest;
    @FXML
    private Button leftButtonUpUpSuggest;
    @FXML
    private HBox hBoxElementUpUpSuggest;
    @FXML
    private Button rightButtonUpUpSuggest;
    @FXML
    private Label labelUpDownSuggest;
    @FXML
    private HBox hBoxUpDownSuggest;
    @FXML
    private Button leftButtonUpDownSuggest;
    @FXML
    private HBox hBoxElementUpDownSuggest;
    @FXML
    private Button rightButtonUpDownSuggest;
    @FXML
    private Button rightButtonUpBoxSuggest;
    @FXML
    private ElementController element1UpUpSuggestController;
    @FXML
    private ElementController element2UpUpSuggestController;
    @FXML
    private ElementController element3UpUpSuggestController;
    @FXML
    private ElementController element4UpUpSuggestController;
    @FXML
    private ElementController element5UpUpSuggestController;
    @FXML
    private ElementController element1UpDownSuggestController;
    @FXML
    private ElementController element2UpDownSuggestController;
    @FXML
    private ElementController element3UpDownSuggestController;
    @FXML
    private ElementController element4UpDownSuggestController;
    @FXML
    private ElementController element5UpDownSuggestController;
    private Collection<PodcastFeed> newChannel;
    private Collection<PodcastFeed> popularChannel;
    private Model model;

    public void initialize() throws NoPodcastAvailable {
        model = Model.getInstance();
        newChannel = model.getRandomPodcastFeedsIn(model.getPodcastFeedManager().getFeeds(), 5);
        popularChannel = model.getRandomPodcastFeedsIn(model.getPodcastFeedManager().getFeeds(), 5);
        reload();
    }

    private void reload() throws NoPodcastAvailable {
        List<ElementController> upPanes = Arrays.asList(element1UpUpSuggestController, element2UpUpSuggestController,
                element3UpUpSuggestController, element4UpUpSuggestController, element5UpUpSuggestController);
        List<ElementController> downPanes = Arrays.asList(element1UpDownSuggestController, element2UpDownSuggestController,
                element3UpDownSuggestController, element4UpDownSuggestController, element5UpDownSuggestController);
        Iterator<ElementController> panesUp = upPanes.iterator();
        Iterator<ElementController> panesDown = downPanes.iterator();
        for (PodcastFeed feed : newChannel) {
            panesUp.next().setCurrentFeed(feed);
        }
        for (PodcastFeed feed : popularChannel) {
            panesDown.next().setCurrentFeed(feed);
        }
    }

    public void reloadUp() throws NoPodcastAvailable {
        newChannel = model.getRandomPodcastFeedsIn(model.getPodcastFeedManager().getFeeds(), 5);
        reload();
    }

    public void reloadDown() throws NoPodcastAvailable {
        popularChannel = model.getRandomPodcastFeedsIn(model.getPodcastFeedManager().getFeeds(), 5);
        reload();
    }

    public void reloadAll() throws NoPodcastAvailable{
        newChannel = model.getRandomPodcastFeedsIn(model.getPodcastFeedManager().getFeeds(), 5);
        popularChannel = model.getRandomPodcastFeedsIn(model.getPodcastFeedManager().getFeeds(), 5);
        reload();
    }
}
