package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private HBox statusBar;
    @FXML
    StatusBarController statusBarController;
    @FXML
    SplitPane splitpane0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final double pos = splitpane0.getDividers().get(0).getPosition();
        splitpane0.getDividers().get(0).positionProperty().addListener((arg0, arg1, arg2) -> splitpane0.getDividers().get(0).setPosition(pos));
    }

    public StatusBarController getStatusBarController() {
        return statusBarController;
    }

    public HBox getStatusBar() {
        return statusBar;
    }
}
