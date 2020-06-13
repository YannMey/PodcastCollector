package edu.PCD.PodcastCollector.Frontend.Controllers.MainPage;


import edu.PCD.PodcastCollector.Frontend.MainApp;
import edu.PCD.PodcastCollector.Frontend.Model;
import edu.PCD.PodcastCollector.Utils.Exception.NoMediaException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.IllformedLocaleException;


public class BarLectureController {
    @FXML
    private Label titreBarLecture;
    @FXML
    private ImageView coverImage;
    @FXML
    private Text descriptionBarLecture;
    @FXML
    private Label timer;
    @FXML
    private Slider progressBarLecture;
    @FXML
    private ProgressBar volume;
    private MediaPlayer mediaPlayer = null;
    private Duration duration;
    private Model model;
    @FXML
    private ImageView downloadImage;
    @FXML
    private ImageView playImage;
    @FXML
    private ImageView favoriteImage;
    private HashMap<String, Boolean> favorites = new HashMap<>();

    public void initialize() {
        model = Model.getInstance();
        model.setLecteur(this);
        reload();
    }

    public void reload() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        if (model.getCurrent() == null) {
            mediaPlayer = null;
            return;
        }

        if (model.getPodcastFeedManager().isConnected())
            mediaPlayer = new MediaPlayer(new Media(model.getCurrent().getURL().toString()));
        else {
            mediaPlayer = new MediaPlayer(new Media(model.getCurrent().getDownloadedURL().toString()));
        }

        if (mediaPlayer.getError() == null) {
            mediaPlayer.setAutoPlay(false);
            mediaPlayer.currentTimeProperty().addListener(ov -> {
                Duration newDuration = mediaPlayer.getCurrentTime();
                if (!progressBarLecture.isValueChanging()) {
                    progressBarLecture.setValue((int) Math.round(newDuration.toSeconds()));
                }
                updateTimeLabel();
            });
            mediaPlayer.setOnReady(() -> {
                duration = mediaPlayer.getMedia().getDuration();
                progressBarLecture.setMin(0.0);
                progressBarLecture.setValue(0.0);
                progressBarLecture.setMax(mediaPlayer.getTotalDuration().toSeconds());
                progressBarLecture.blockIncrementProperty().setValue(1);
                timer.setText(calculateTime(Duration.ZERO));
                playImage.setImage(new Image(MainApp.class.getResource("images/png/play-button.png").toString(), 64, 64, true, true));
            });
            playImage.setImage(new Image(MainApp.class.getResource("images/loading.gif").toExternalForm(), 64, 64, true, true));
            progressBarLecture.setOnMouseReleased((MouseEvent mouseEvent) -> {
                mediaPlayer.seek(Duration.seconds(progressBarLecture.getValue()));
            });
            mediaPlayer.volumeProperty().addListener(ov -> {
                volume.setProgress(mediaPlayer.getVolume());
            });

            volume.setProgress(mediaPlayer.getVolume());
            this.titreBarLecture.setText(model.getCurrent().getName());
            String[] stringArray = model.getCurrent().getDescription().split("-");
            String description = "Description : " + stringArray[stringArray.length - 1];
            this.descriptionBarLecture.setText(description.substring(0, Math.min(description.length() - 1, 180)));
            this.descriptionBarLecture.setWrappingWidth(350);
            this.descriptionBarLecture.maxHeight(10);
            if (model.getCurrent().isDownloaded()) {
                downloadImage.setImage(new Image(MainApp.class.getResource("images/downloaded.png").toString(), 64, 64, true, true));
            } else {
                downloadImage.setImage(new Image(MainApp.class.getResource("images/png/download.png").toString(), 64, 64, true, true));
            }
            if (favorites.containsKey(model.getCurrent().getHash())) {
                favoriteImage.setImage(new Image(MainApp.class.getResource("images/png/favorite.png").toString(), 64, 64, true, true));
            } else {
                favoriteImage.setImage(new Image(MainApp.class.getResource("images/png/notfavorite.png").toString(), 64, 64, true, true));
            }

            if (model.getPodcastFeedManager().isConnected() && model.getCurrent().getImageURL() != null) {

                this.coverImage.setImage(new Image(model.getCurrent().getImageURL().toString()));
            }

        }
    }

    public void setAutoplay(Boolean b) {
        mediaPlayer.setAutoPlay(b);
    }

    @FXML
    void play() throws Exception {
        if (mediaPlayer == null || model.getCurrent() == null) {
            return;
        }
        if (model.getToReload()) reload();
        if (mediaPlayer.getError() != null) {
            model.setCurrent(model.getRandomPodcast());
            play();
            return;
        }
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
            // don't do anything in these states
            return;
        }

        if (status == MediaPlayer.Status.PAUSED
                || status == MediaPlayer.Status.READY
                || status == MediaPlayer.Status.STOPPED) {
            // rewind the stream if we're sitting at the end
            if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
            }
            mediaPlayer.play();
        }
    }

    @FXML
    private void pause() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.pause();
    }

    private void updateTimeLabel() {
        if (mediaPlayer == null) {
            return;
        }
        timer.setText(calculateTime(mediaPlayer.getCurrentTime()));
    }

    private String calculateTime(Duration elapsed) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                    durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }


    @FXML
    public void volumeUp() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.setVolume(Math.min(mediaPlayer.getVolume() + 1 / 25.0, 1.0));
    }

    @FXML
    public void volumeDown() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.setVolume(Math.max(mediaPlayer.getVolume() - 1 / 25.0, 0.0));
    }

    @FXML
    public void download() throws IOException {
        if (mediaPlayer == null) {
            return;
        }
        model.getCurrent().download();
        if (model.getCurrent().isDownloaded()) {
            model.getSetDownloadController().addDownload(model.getCurrent());
            downloadImage.setImage(new Image(MainApp.class.getResource("images/downloaded.png").toString(), 64, 64, true, true));
        }
    }

    @FXML
    public void favorite() {
        if (mediaPlayer == null) {
            return;
        }
        //TODO add the favorite to the backend
        if (!favorites.containsKey(model.getCurrent().getHash())) {
            favoriteImage.setImage(new Image(MainApp.class.getResource("images/png/favorite.png").toString(), 64, 64, true, true));
            favorites.put(model.getCurrent().getHash(), Boolean.TRUE);
        } else {
            favoriteImage.setImage(new Image(MainApp.class.getResource("images/png/notfavorite.png").toString(), 64, 64, true, true));
            favorites.remove(model.getCurrent().getHash());
        }

    }
}
