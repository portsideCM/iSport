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
import src.Preferences.Sport;
import src.Preferences.SportList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Pane, Sport> paneToSportMap;

    private List<Pane> sportPanes;

    private final Background selected = new Background(new BackgroundFill(Color.web("rgba(50,50,50,0.8)"), new CornerRadii(15), Insets.EMPTY));
    private final Background notSelected = new Background(new BackgroundFill(Color.web("rgba(175,175,175,0.8)"), new CornerRadii(15), Insets.EMPTY));

    @FXML
    public void initialize() {
        // Adds pane to sport mapping and all sports a panes to a map and list
        // Must be done here as panes are created on initialize
        paneToSportMap = new HashMap<>(Map.of(
                rugbyPane, Sport.RUGBY,
                footballPane, Sport.FOOTBALL,
                volleyballPane, Sport.VOLLEYBALL,
                tennisPane, Sport.TENNIS,
                cricketPane, Sport.CRICKET,
                cyclingPane, Sport.CYCLING,
                runningPane, Sport.RUNNING,
                rowingPane, Sport.ROWING,
                hikingPane, Sport.HIKING,
                sailingPane, Sport.SAILING
        ));
        sportPanes = new ArrayList<>(paneToSportMap.keySet());

        //Highlights selected sports
        List<Sport> selectedSports = SportList.get();
        for (Pane sportP : sportPanes) {
            if (selectedSports.contains(paneToSportMap.get(sportP))) {
                sportP.setStyle("-fx-background-color: rgba(50, 50, 50, 0.8);");
            }
        }

    }

    @FXML
    private void selectSport(MouseEvent event) {
        Pane paneClicked = (Pane) event.getSource();
        if (paneClicked.getBackground().getFills().get(0).getFill().equals(notSelected.getFills().get(0).getFill())) // select a sport
            paneClicked.setBackground(selected);
        else // deselect a sport
            paneClicked.setBackground(notSelected);
    }

    @FXML
    private void loadHomePage(MouseEvent event) throws IOException {

        // record which sports are selected
        paneToSportMap = new HashMap<>(Map.of(
                rugbyPane, Sport.RUGBY,
                footballPane, Sport.FOOTBALL,
                volleyballPane, Sport.VOLLEYBALL,
                tennisPane, Sport.TENNIS,
                cricketPane, Sport.CRICKET,
                cyclingPane, Sport.CYCLING,
                runningPane, Sport.RUNNING,
                rowingPane, Sport.ROWING,
                hikingPane, Sport.HIKING,
                sailingPane, Sport.SAILING
        ));
        sportPanes = new ArrayList<>(paneToSportMap.keySet());
        SportList.clear();
        for (Pane pane : sportPanes) {
            if (pane.getBackground().getFills().get(0).getFill().equals(selected.getFills().get(0).getFill()))
                SportList.add(paneToSportMap.get(pane));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/iSport/Frames/HomePage/HomePage.fxml"));
        Parent anchorHomePage = loader.load(); // currently anchorHomePage is the StackPane
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
