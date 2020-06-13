package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;

import edu.PCD.PodcastCollector.Backend.Podcast;
import edu.PCD.PodcastCollector.Frontend.MainApp;
import edu.PCD.PodcastCollector.Frontend.Model;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.concurrent.*;


public class Element2Controller {
    @FXML
    private ImageView imageElement2;
    @FXML
    private Label titreElement2;
    @FXML
    private Text descriptionElement2;
    @FXML
    private Button playElement2;
    @FXML
    private Button downloadElement2;
    @FXML
    private Label liveElement2;
    @FXML
    private ImageView downloadImageRecent;
    @FXML
    private Label ReadElement;
    private Podcast current;

    public void initData(Podcast podcast) {
        this.current = podcast;
        imageElement2.setImage(new Image(current.getImageURL().toString()));
        titreElement2.setText(current.getName());
        String description = "Description : " + current.getDescription();
        descriptionElement2.setText(description.substring(0, Math.min(description.length(), 60)));
        liveElement2.setText("Recent");
        if (podcast.isDownloaded()) {
            downloadImageRecent.setImage(new Image(MainApp.class.getResource("images/downloaded.png").toString(), 64, 64, true, true));
        } else {
            downloadImageRecent.setImage(new Image(MainApp.class.getResource("images/png/download.png").toString(), 64, 64, true, true));
        }
    }

    @FXML
    public void downloadRecent() {

        ScheduledExecutorService taskExecutor = Executors.newScheduledThreadPool(1);
        final ScheduledFuture<?> scheduledFuture=taskExecutor.schedule(
                new Runnable() {
                    @Override
                    public void run() {


                    }
                }, 0, TimeUnit.MILLISECONDS
        );
        current.download();
        if (current.isDownloaded()) {
            downloadImageRecent.setImage(new Image(MainApp.class.getResource("images/downloaded.png").toString(), 64, 64, true, true));
            try {
                Model.getInstance().getSetDownloadController().addDownload(current);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    @FXML
    public void playRecent() {
        BarLectureController barLectureController = Model.getInstance().getLecteur();
        Model.getInstance().setCurrent(current);
        barLectureController.reload();
        barLectureController.setAutoplay(true);

        ReadElement.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
