package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SplitSuggestController implements Initializable {
    @FXML
    SplitPane splitpane1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final double pos = splitpane1.getDividers().get(0).getPosition();
        splitpane1.getDividers().get(0).positionProperty().addListener((arg0, arg1, arg2) -> splitpane1.getDividers().get(0).setPosition(pos));
    }
}
