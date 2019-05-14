package src.iSport.Frames.HomePage;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Button burgerButton;

    @FXML
    private AnchorPane anchorHomePage;

    @FXML
    private StackPane masterContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loadWeatherInfoPage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("src/iSport/Frames/WeatherInfoPage/WeatherInfoPage.fxml"));

        root.translateXProperty().set(0);
        masterContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(e -> {
            masterContainer.getChildren().remove(kf);
        });
        timeline.play();
    }
}
