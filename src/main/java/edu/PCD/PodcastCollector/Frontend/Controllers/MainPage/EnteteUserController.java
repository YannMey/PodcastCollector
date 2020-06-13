package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import edu.PCD.PodcastCollector.Frontend.HackyStuff.EE.TicTacToe;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class EnteteUserController   {

    @FXML
    private ImageView imageLogo;
    @FXML
    private Label titreLogo;
    private Object global;

    public void popEE(){
        new TicTacToe().start(new Stage());
    }

}
