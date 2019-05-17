package src.iSport.Frames.WeatherInfoPage;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import src.API.APIConnectionSingleton;
import src.API.CurrentWeather;
import src.API.Forecast;
import src.API.ForecastData;
import src.Images.Backgrounds;
import src.iSport.Frames.HomePage.HomePageController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class WeatherInfoPageController {
    @FXML
    private ImageView background;
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

    private static final String cityName = "Cambridge,uk";

    //Loads up all background images
    private Backgrounds bg = new Backgrounds();

    @FXML
    public void initialize() {
        // Sets background image
        background.setImage(bg.getBestBackground());

        try {
            APIConnectionSingleton conn = APIConnectionSingleton.getAPIConnection();
            CurrentWeather currentWeather = conn.getCurrentWeather(cityName, true);
            Forecast forecast = conn.getForecast(cityName, true);
            ForecastData today = forecast.nextDayWeather(LocalDate.now(), 0, ZoneId.systemDefault());
            String tempMin = String.valueOf(Math.round(HomePageController.convert(today.TempMin, today)));
            String tempMax = String.valueOf(Math.round(HomePageController.convert(today.TempMax, today)));
            tempLabel.setText(tempMin + "°-" + tempMax + "°");
            pressureLabel.setText(currentWeather.Pressure + " hPa");
            humidityLabel.setText(Math.round(currentWeather.Humidity) + "%");
            windLabel.setText(HomePageController.windAsString(currentWeather.WindSpeed, currentWeather.WindDir));
            cloudCoverLabel.setText(currentWeather.CloudCover + "%");
            precipitationLabel.setText(currentWeather.Rain1h +" mm");
            visibilityLabel.setText(currentWeather.Visibility + " m");
            LocalTime localSunRise = LocalTime.from(currentWeather.Sunrise.atZone(ZoneId.systemDefault()));
            sunriseLabel.setText(localSunRise.format(DateTimeFormatter.ofPattern("HH:mm")));
            LocalTime localSunSet = LocalTime.from(currentWeather.Sunset.atZone(ZoneId.systemDefault()));
            sunsetLabel.setText(localSunSet.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        catch(IOException e) {
            // Have some nice error message b/c the API failed here
            e.printStackTrace();
        }
    }

    @FXML
    private void loadHomePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/iSport/Frames/HomePage/HomePage.fxml"));
        Parent anchorHomePage = loader.load(); // currently anchorHomePage is the StackPane
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
