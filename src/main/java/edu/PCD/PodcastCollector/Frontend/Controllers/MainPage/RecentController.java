package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import edu.PCD.PodcastCollector.Backend.Podcast;
import edu.PCD.PodcastCollector.Frontend.MainApp;
import edu.PCD.PodcastCollector.Frontend.Model;
import edu.PCD.PodcastCollector.Utils.Exception.NoPodcastAvailable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class RecentController {
    @FXML
    private Label titreRecent;
    @FXML
    private ScrollPane scrollRecent;
    @FXML
    private ListView<HBox> listeRecent;
    @FXML
    private HBox elementRecent;
    @FXML
    private FlowPane flowplaneMain;
    private Collection<Podcast> currentDisplay;
    private List<Element2Controller> element2Controllers=new ArrayList<>();
    private Model model;

    public void initialize() throws IOException {
        model = Model.getInstance();
        model.setRecentController(this);
        currentDisplay=model.getPodcastFeedManager().getLatestPodcasts(20);
        reload();
    }
    public void reinit(){
        currentDisplay=model.getPodcastFeedManager().getLatestPodcasts(20);
        Iterator<Podcast> iterator=currentDisplay.iterator();
        for(Element2Controller element2Controller:element2Controllers){
            element2Controller.initData(iterator.next());
        }
    }
    private void reload() throws  IOException {
        Iterator<Podcast> iterator=currentDisplay.iterator();

        for (int i = 0; i < currentDisplay.size(); i++) {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("contenu/user/element2.fxml"));
            HBox current=loader.load();
            Element2Controller element2Controller=loader.getController();
            element2Controllers.add(element2Controller);
            element2Controller.initData(iterator.next());
            flowplaneMain.getChildren().add(current);

        }
    }
}
