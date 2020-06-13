package edu.PCD.PodcastCollector.Frontend;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import edu.PCD.PodcastCollector.Backend.DataBaseManager;
import edu.PCD.PodcastCollector.Backend.DatabasePodcastFeedStore;
import edu.PCD.PodcastCollector.Backend.PodcastFeedManager;
import edu.PCD.PodcastCollector.Frontend.Controllers.ErrorController;
import edu.PCD.PodcastCollector.Frontend.Controllers.MainPage.MainController;
import edu.PCD.PodcastCollector.Frontend.Controllers.MainPage.StatusBarController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.NotYetConnectedException;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(MainApp::showError);
        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread th, Throwable ex) {
                System.out.println("Uncaught exception: " + ex);
            }
        };
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml") );
        Parent root=loader.load();
        BorderlessScene scene = new BorderlessScene(stage, StageStyle.UNDECORATED, root, 1280, 800);
        stage.setScene(scene);
        root.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        scene.setResizable(false);
        MainController mainController=loader.getController();
        scene.setMoveControl(mainController.getStatusBar());
        StatusBarController statusBarController=mainController.getStatusBarController();
        if (statusBarController!=null){
            statusBarController.initData(scene);
        }
        else{
            throw new NotYetConnectedException();
        }
        new JMetro(Style.LIGHT).setScene(scene);
        scene.setTransparentWindowStyle("-fx-background-color:rgb(0,0,0,0.9); -fx-border-color:firebrick; -fx-border-width:2px;");

        stage.setTitle("Podcast Collector");
        stage.setScene(scene);
        stage.show();
        scene.maximizeStage();
        scene.maximizeStage();
    }

    public void init() {
        try {
            DataBaseManager.connect(DataBaseManager.DATABASE_FILENAME);
            DatabasePodcastFeedStore databasePodcastFeedStore = new DatabasePodcastFeedStore();
            PodcastFeedManager podcastFeedManager = new PodcastFeedManager(databasePodcastFeedStore);

            podcastFeedManager.update();
            Model.getInstance().setPodcastFeedManager(podcastFeedManager);
            Model.getInstance().initData();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in "+t);

        }
    }

    private static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("Error.fxml"));
        try {
            Parent root = loader.load();
            ((ErrorController)loader.getController()).setErrorText(errorMsg.toString());
            dialog.setScene(new Scene(root, 400, 400));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
