package src.iSport.Frames.HomePage;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Button burgerButton;
    @FXML
    private StackPane masterContainer;
    @FXML
    private Label celsiusLabel;
    @FXML
    private Label fahrenheitLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: load weather information from the weather API
    }

    @FXML
    private void loadWeatherInfoPage(MouseEvent event) throws IOException {
        Parent anchorWeatherInfo = FXMLLoader.load(getClass().getClassLoader().getResource("src/iSport/Frames/WeatherInfoPage/WeatherInfoPage.fxml"));
        Scene scene = burgerButton.getScene();

        anchorWeatherInfo.translateXProperty().set(-scene.getWidth());
        masterContainer.getChildren().add(anchorWeatherInfo);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(anchorWeatherInfo.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    private void switchTempUnit(MouseEvent event) {
        if(celsiusLabel.getTextFill()==Color.WHITESMOKE) { // Convert Celsius to Fahrenheit
            // TODO: change from C to F and display the new temperature values
            celsiusLabel.setTextFill(Color.web("#b2b2b2"));
            celsiusLabel.setFont(new Font("Consolas Bold",18));
            fahrenheitLabel.setTextFill(Color.WHITESMOKE);
            fahrenheitLabel.setFont(new Font("Consolas Bold",23));
        } else { // Convert Fahrenheit to Celsius
            // TODO: change from F to C and display the new temperature values
            fahrenheitLabel.setTextFill(Color.web("#b2b2b2"));
            fahrenheitLabel.setFont(new Font("Consolas Bold",18));
            celsiusLabel.setTextFill(Color.WHITESMOKE);
            celsiusLabel.setFont(new Font("Consolas Bold",23));
        }
    }
}
