package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;


import edu.PCD.PodcastCollector.Backend.Podcast;
import edu.PCD.PodcastCollector.Backend.PodcastFeed;
import edu.PCD.PodcastCollector.Frontend.MainApp;
import edu.PCD.PodcastCollector.Frontend.Model;
import edu.PCD.PodcastCollector.Utils.Exception.NoMediaException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class ElementController {
    @FXML
    private Pane paneElementSuggest;
    @FXML
    private ImageView imageElementSuggest;
    @FXML
    private Label nameElementSuggest;
    @FXML
    private TextArea descriptionElementSuggest;
    private Podcast current = null;
    private PodcastFeed currentFeed = null;

    public Podcast getCurrent() {
        return current;
    }

    public void setCurrent(Podcast current) {
        this.current = current;
        this.currentFeed = null;
        reload();
    }

    public void setCurrentFeed(PodcastFeed currentFeed) {
        this.currentFeed = currentFeed;
        this.current = null;
        reloadFeed();
    }

    public void reloadFeed() {
        nameElementSuggest.setText(currentFeed.getName());
        if (currentFeed.getProvider() != null && !currentFeed.getProvider().equals("PodcastCollector")) {
            imageElementSuggest.setImage(new Image(currentFeed.getProvider()));
            Image img = imageElementSuggest.getImage();
            double w = 0;
            double h = 0;

            double ratioX = imageElementSuggest.getFitWidth() / img.getWidth();
            double ratioY = imageElementSuggest.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            reducCoeff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageElementSuggest.setX((imageElementSuggest.getFitWidth() - w) / 2);
            imageElementSuggest.setY((imageElementSuggest.getFitHeight() - h) / 2);
        }
    }

    public void reload() {
        nameElementSuggest.setText(current.getName());
        if (current.getImageURL() != null) {
            imageElementSuggest.setImage(new Image(current.getImageURL().toString()));
            Image img = imageElementSuggest.getImage();
            double w = 0;
            double h = 0;

            double ratioX = imageElementSuggest.getFitWidth() / img.getWidth();
            double ratioY = imageElementSuggest.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            reducCoeff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageElementSuggest.setX((imageElementSuggest.getFitWidth() - w) / 2);
            imageElementSuggest.setY((imageElementSuggest.getFitHeight() - h) / 2);
        }
    }

    public void doSomething() throws NoMediaException {
        if (currentFeed == null) {
            if (current != null) {
                Model.getInstance().setCurrent(current);
                Model.getInstance().getLecteur().reload();
            }
        } else {
            if (current == null) {
                //TODO showPopUp Feed here
            } else {
                throw new NoMediaException("Wtf this is not possible");
            }
        }
    }
}
