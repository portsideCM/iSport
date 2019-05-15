package src.iSport.Frames.SportPage;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class SportPageController {

    @FXML
    private Pane rugbyPane;
    @FXML
    private Pane footballPane;
    @FXML
    private Pane volleyballPane;
    @FXML
    private Pane tennisPane;
    @FXML
    private Pane cricketPane;
    @FXML
    private Pane cyclingPane;
    @FXML
    private Pane runningPane;
    @FXML
    private Pane rowingPane;
    @FXML
    private Pane hikingPane;
    @FXML
    private Pane sailingPane;
    @FXML
    private Label doneLabel;
    @FXML
    private AnchorPane anchorSport;

    @FXML
    private void selectSport(MouseEvent event) {
        Pane paneClicked = (Pane) event.getSource();
        if (paneClicked.getBackground().getFills().get(0).getFill().equals(Color.web("rgba(175,175,175,0.8)"))) // select a sport
            paneClicked.setBackground(new Background(new BackgroundFill(Color.web("rgba(50,50,50,0.8)"), new CornerRadii(15), Insets.EMPTY)));
        else // deselect a sport
            paneClicked.setBackground(new Background(new BackgroundFill(Color.web("rgba(175,175,175,0.8)"), new CornerRadii(15), Insets.EMPTY)));
    }

    @FXML
    private void loadHomePage(MouseEvent event) throws IOException {
        // TODO: record which sports are selected and change the home page layout accordingly

        Parent anchorHomePage = FXMLLoader.load(getClass().getClassLoader().getResource("src/iSport/Frames/HomePage/HomePage.fxml")); // currently anchorHomePage is the StackPane
        anchorHomePage = (Parent) ((StackPane) anchorHomePage).getChildren().get(0); // now anchorHomePage is the actual AnchorPane that we want
        Parent current = anchorSport;
        Scene scene = doneLabel.getScene();

        current.translateXProperty().set(0);
        StackPane masterContainer = (StackPane) scene.getRoot();
        if (!masterContainer.getChildren().contains(anchorHomePage))
            masterContainer.getChildren().add(0, anchorHomePage);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(current.translateXProperty(), -scene.getWidth(), Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(e -> {
            masterContainer.getChildren().remove(anchorSport);
        });
        timeline.play();
    }
}
