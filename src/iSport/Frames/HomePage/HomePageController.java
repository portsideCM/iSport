package src.iSport.Frames.HomePage;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import src.API.APIConnectionSingleton;
import src.API.CurrentWeather;
import src.API.Forecast;
import src.API.ForecastData;
import src.Images.Backgrounds;
import src.Images.Icons;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class HomePageController implements Initializable {

    @FXML
    private ImageView background;
    @FXML
    private ImageView dayIcon1;
    @FXML
    private ImageView dayIcon2;
    @FXML
    private ImageView dayIcon3;
    @FXML
    private ImageView dayIcon4;
    @FXML
    private Button burgerButton;
    @FXML
    private StackPane masterContainer;
    @FXML
    private Label celsiusLabel;
    @FXML
    private Label fahrenheitLabel;
    @FXML
    private Pane mainTempPane;
    @FXML
    private Label mainTempLabel;
    @FXML
    private ImageView mainTempIcon;
    @FXML
    private Label locationLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label info1Label;
    @FXML
    private Label info2Label;
    @FXML
    private Label info3Label;
    @FXML
    private Label day1Label;
    @FXML
    private Label day2Label;
    @FXML
    private Label day3Label;
    @FXML
    private Label day4Label;
    @FXML
    private Label day1TempLabel;
    @FXML
    private Label day2TempLabel;
    @FXML
    private Label day3TempLabel;
    @FXML
    private Label day4TempLabel;

    //Loads up all background images
    private Backgrounds bg = new Backgrounds();
    //Load up all weather icons
    private Icons ic = new Icons();

    private static final String cityName = "Cambridge,uk";

    // C for celsius, F for fahrenheit
    private String tempUnit = "C";


    //Called when page is loaded
    @Override
    public void initialize(URL url, ResourceBundle rb) {

            background.setImage(bg.SAILING_BG);


            //Loads weather info and displays it
            try {
                APIConnectionSingleton conn = APIConnectionSingleton.getAPIConnection();
                CurrentWeather currentWeather = conn.getCurrentWeather(cityName, true);
                Forecast forecast = conn.getForecast(cityName, true);


                //Sets main weather info
                if (tempUnit == "C") {
                    mainTempLabel.setText(String.valueOf(Math.round(currentWeather.getTempInCelsius(currentWeather.Temp))) + "°");
                } else {
                    mainTempLabel.setText(String.valueOf(Math.round(currentWeather.getTempInFarenheit(currentWeather.Temp))) + "°");
                }
                mainTempIcon.setImage(ic.iconCalc(currentWeather.WeatherId));


                //Set day 1 weather info
                ForecastData day1 = forecast.nextDayWeather(LocalDate.now(), 1, ZoneId.of("GMT"));
                dayIcon1.setImage(ic.iconCalc(day1.WeatherId));
                String tempMin1 = String.valueOf(Math.round(convert(day1.TempMin, day1)));
                String tempMax1 = String.valueOf(Math.round(convert(day1.TempMax, day1))) ;
                day1TempLabel.setText(tempMin1 + "°-" + tempMax1 + "°");


                //Set day 2 weather info
                ForecastData day2 = forecast.nextDayWeather(LocalDate.now(), 2, ZoneId.of("GMT"));
                dayIcon2.setImage(ic.iconCalc(day2.WeatherId));
                String tempMin2 = String.valueOf(Math.round(convert(day2.TempMin, day2)));
                String tempMax2 = String.valueOf(Math.round(convert(day2.TempMin, day2)));
                day2TempLabel.setText(tempMin2 + "°-" + tempMax2 + "°");

                //Set day 3 weather info
                ForecastData day3 = forecast.nextDayWeather(LocalDate.now(), 3, ZoneId.of("GMT"));
                dayIcon3.setImage(ic.iconCalc(day3.WeatherId));
                String tempMin3 = String.valueOf(Math.round(convert(day3.TempMin, day3)));
                String tempMax3 = String.valueOf(Math.round(convert(day3.TempMin, day3)));
                day3TempLabel.setText(tempMin3 + "°-" + tempMax3 + "°");

                //Set day 4 weather info
                ForecastData day4 = forecast.nextDayWeather(LocalDate.now(), 4, ZoneId.of("GMT"));
                dayIcon4.setImage(ic.iconCalc(day4.WeatherId));
                String tempMin4 = String.valueOf(Math.round(convert(day4.TempMin, day4)));
                String tempMax4 = String.valueOf(Math.round(convert(day4.TempMin, day4)));
                day4TempLabel.setText(tempMin4 + "°-" + tempMax4 + "°");

            }
            catch(IOException e) {
                // TODO: Have some nice error message b/c the API failed here
                e.printStackTrace();
            }


    }

    @FXML
    private void loadSportPage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/iSport/Frames/SportPage/SportPage.fxml"));
        Parent anchorSport = loader.load();
        Scene scene = burgerButton.getScene();

        anchorSport.translateXProperty().set(-scene.getWidth());
        masterContainer.getChildren().add(anchorSport);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(anchorSport.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    private void loadWeatherInfoPage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/iSport/Frames/WeatherInfoPage/WeatherInfoPage.fxml"));
        Parent anchorWeatherInfo = loader.load();
        Scene scene = mainTempPane.getScene();



        anchorWeatherInfo.translateYProperty().set(scene.getHeight());
        masterContainer.getChildren().add(anchorWeatherInfo);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(anchorWeatherInfo.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    private void switchTempUnit(MouseEvent event) throws MalformedURLException {
        if(celsiusLabel.getTextFill()==Color.WHITESMOKE) { // Convert Celsius to Fahrenheit


            tempUnit = "F";

            celsiusLabel.setTextFill(Color.web("#b2b2b2"));
            celsiusLabel.setFont(new Font("Consolas Bold",18));
            fahrenheitLabel.setTextFill(Color.WHITESMOKE);
            fahrenheitLabel.setFont(new Font("Consolas Bold",23));
        } else { // Convert Fahrenheit to Celsius

            tempUnit = "C";

            fahrenheitLabel.setTextFill(Color.web("#b2b2b2"));
            fahrenheitLabel.setFont(new Font("Consolas Bold",18));
            celsiusLabel.setTextFill(Color.WHITESMOKE);
            celsiusLabel.setFont(new Font("Consolas Bold",23));
        }

        //updates Display
        initialize(new URL("file:/C:/Users/Taylor/Documents/Cambridge/iSport/out/production/iSport/src/iSport/Frames/HomePage/HomePage.fxml"), null);

    }

    //Decides what unit to display the temp in
    public double convert(double T, ForecastData FD){
        if (tempUnit == "C"){
            return FD.getTempInCelsius(T);
        } else {
            return FD.getTempInFarenheit(T);
        }
    }
}
