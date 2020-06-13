
package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import edu.PCD.PodcastCollector.Backend.Podcast;
import edu.PCD.PodcastCollector.Frontend.MainApp;
import edu.PCD.PodcastCollector.Frontend.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class DownloadController {
    @FXML
    private Label titreDownload;
    @FXML
    private ScrollPane scrollDownload;
    @FXML
    private ListView listeDownload;
    @FXML
    private HBox elementDownload;
    private Model model;
    private Collection<Podcast> currentDisplay;
    private List<Element2Controller> element2Controllers=new ArrayList<>();
    @FXML
    private FlowPane flowPlaneSecond;
    public void initialize() throws IOException {
        model = Model.getInstance();
        model.setSetDownloadController(this);
        currentDisplay=model.getPodcastFeedManager().getDownloadedPodcasts();
        reload();
    }
    public void reload() throws IOException {
        Iterator<Podcast> iterator=currentDisplay.iterator();
        for (int i = 0; i < currentDisplay.size(); i++) {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("contenu/user/element2.fxml") );
            HBox current=loader.load();
            Element2Controller element2Controller=loader.getController();
            element2Controllers.add(element2Controller);
            element2Controller.initData(iterator.next());
            flowPlaneSecond.getChildren().add(current);
        }

    }

    public void addDownload(Podcast podcast) throws IOException {
        currentDisplay.add(podcast);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../contenu/user/element2.fxml") );
        HBox hBox=loader.load();
        Element2Controller element2Controller=loader.getController();
        element2Controllers.add(element2Controller);
        element2Controller.initData(podcast);
        flowPlaneSecond.getChildren().add(hBox);
    }

}
