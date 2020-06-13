package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private SplitPane splitpane2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final double pos = splitpane2.getDividers().get(0).getPosition();
        splitpane2.getDividers().get(0).positionProperty().addListener((arg0, arg1, arg2) -> splitpane2.getDividers().get(0).setPosition(pos));
    }
}
