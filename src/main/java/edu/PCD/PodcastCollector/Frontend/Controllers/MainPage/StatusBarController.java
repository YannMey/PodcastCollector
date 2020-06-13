package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.sun.scenario.effect.Blend;
import edu.PCD.PodcastCollector.Backend.Category;
import edu.PCD.PodcastCollector.Frontend.HackyStuff.PixelatedImageView;
import edu.PCD.PodcastCollector.Frontend.MainApp;
import edu.PCD.PodcastCollector.Frontend.Model;
import edu.PCD.PodcastCollector.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusBarController implements Initializable {
    @FXML
    private Button quit;
    @FXML
    private Button maximize;
    @FXML
    private Button hide;
    @FXML
    private Text labelSearchTop;
    @FXML
    private MenuButton labelCategorieTop;
    @FXML
    private Button reglageTop;
    @FXML
    private Button iconeTop;
    @FXML
    private Button profilTop;
    @FXML
    private ImageView maximizeImage;
    @FXML
    private ImageView minimizeImage;
    @FXML
    private ImageView quitImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(MainApp.class.getResource("images/hide_icon.png").toString(), 64, 64, true, true);
        Image image2 = new Image(MainApp.class.getResource("images/maximise_icon.png").toString(), 64, 64, true, true);
        Image image3 = new Image(MainApp.class.getResource("images/quit_icon.png").toString(), 64, 64, true, true);

        minimizeImage.setImage(image);
        maximizeImage.setImage(image2);
        quitImage.setImage(image3);

        for (Category category : Model.getInstance().getAllCategories()) {
            MenuItem menuItem = new MenuItem(category.getName());
            labelCategorieTop.getItems().add(menuItem);
            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    labelCategorieTop.setText(((MenuItem)e.getSource()).getText());
                }
            };
            menuItem.setOnAction(event1);
        }

    }

    private BorderlessScene scene;

    public void initData(BorderlessScene scene) {
        this.scene = scene;
    }

    @FXML
    private void hideApplication() {
        scene.minimizeStage();
    }

    @FXML
    private void quitApplication() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void maximizeApplication() {
        scene.maximizeStage();
    }


}
