package src.iSport.Frames.WeatherInfoPage;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherInfoPageController implements Initializable {

    @FXML
    private Label tempLabel;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label windLabel;
    @FXML
    private Label cloudCoverLabel;
    @FXML
    private Label precipitationLabel;
    @FXML
    private Label visibilityLabel;
    @FXML
    private Label sunriseLabel;
    @FXML
    private Label sunsetLabel;
    @FXML
    private Label backLabel;
    @FXML
    private AnchorPane anchorWeatherInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: load weather information from the weather API
        //       Label variables have been provided above
        //       Use label.SetText()
    }

    @FXML
    private void loadHomePage(MouseEvent event) throws IOException {
        Parent anchorHomePage = FXMLLoader.load(getClass().getClassLoader().getResource("src/iSport/Frames/HomePage/HomePage.fxml")); // currently anchorHomePage is the StackPane
        anchorHomePage = (Parent) ((StackPane) anchorHomePage).getChildren().get(0); // now anchorHomePage is the actual AnchorPane that we want
        Parent current = anchorWeatherInfo;
        Scene scene = backLabel.getScene();

        current.translateYProperty().set(0);
        StackPane masterContainer = (StackPane) scene.getRoot();
        if (!masterContainer.getChildren().contains(anchorHomePage))
            masterContainer.getChildren().add(0, anchorHomePage);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(current.translateYProperty(), scene.getHeight(), Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(e -> {
            masterContainer.getChildren().remove(anchorWeatherInfo);
        });
        timeline.play();
    }
}
